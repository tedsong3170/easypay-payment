package song.pg.payment.api.payment.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import song.pg.payment.api.payment.PaymentService;
import song.pg.payment.method.findAll.v1.proto.MethodFindAllV1;
import song.pg.payment.method.findAll.v1.proto.PaymentMethodFindAllServiceGrpc;
import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.customer.CustomerEntity;
import song.pg.payment.models.customer.CustomerVo;
import song.pg.payment.models.payment.PaymentInfoEntity;
import song.pg.payment.models.payment.approval.RequestPaymentApproval;
import song.pg.payment.models.payment.ledger.PaymentLedgerEntity;
import song.pg.payment.models.payment.method.PaymentMethodType;
import song.pg.payment.models.payment.method.ResponsePaymentMethod;
import song.pg.payment.models.payment.ready.RequestPaymentReady;
import song.pg.payment.models.payment.ready.ResponsePaymentReady;
import song.pg.payment.models.payment.request.RequestPaymentRequest;
import song.pg.payment.models.payment.request.ResponsePaymentRequest;
import song.pg.payment.repository.CustomerRepository;
import song.pg.payment.repository.PaymentInfoRepository;
import song.pg.payment.repository.PaymentLedgerRepository;
import song.pg.payment.token.create.v1.proto.TokenCreateGrpc;
import song.pg.payment.token.create.v1.proto.TokenCreateV1;
import song.pg.payment.utils.ExceptionEnum;
import song.pg.payment.utils.JsonUtil;
import song.pg.payment.utils.JwtUtil;
import song.pg.payment.utils.KnownException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService
{
  private final CustomerRepository customerRepository;
  private final PaymentInfoRepository paymentInfoRepository;
  private final PaymentLedgerRepository paymentLedgerRepository;
  private final JwtUtil jwtUtil;
  private final KafkaTemplate<String, String> kafkaTemplate;

  private static final String EASY_PAYMENT = "EASY";
  private static final String PAYMENT_STATUS_REQUEST = "REQUEST";

  @GrpcClient("token")
  private PaymentMethodFindAllServiceGrpc.PaymentMethodFindAllServiceBlockingStub paymentMethodFindAllBlockingStub;
  @GrpcClient("token")
  private TokenCreateGrpc.TokenCreateBlockingStub tokenCreateBlockingStub;


  @Override
  @Transactional
  public CommonResponse<ResponsePaymentReady> readyPayment(final String ci, final RequestPaymentReady requestPaymentReady)
  {
    /**
     * -1. 사용자 정보 조회
     * 0. 사용자 정보 등록
     * 1. 결제 정보 등록
     * 2. 결제 수단 조회
     * 3. 리턴
     */
    CustomerEntity customer = customerRepository.findByCiAndMid(ci, requestPaymentReady.getMid());

    if (customer == null)
    {
      customer = new CustomerEntity(ci, requestPaymentReady.getMid());
      customerRepository.save(customer);
    }

    Long count = paymentInfoRepository.countByStatusIsNotAndOrderIdAndMid("CANCEL", requestPaymentReady.getOrderId(), requestPaymentReady.getMid());

    if (count > 0)
    {
      throw new KnownException(ExceptionEnum.ALREADY_EXIST_PAYMENT);
    }

    PaymentInfoEntity paymentInfo = new PaymentInfoEntity(
      customer.getDi(),
      requestPaymentReady
    );

    paymentInfoRepository.save(paymentInfo);

    MethodFindAllV1.Response paymentMethodFindAllResponse = paymentMethodFindAllBlockingStub.findAllPaymentMethod(
      MethodFindAllV1.Request
        .newBuilder()
        .setDi(customer.getDi())
        .build()
    );

    log.debug("grpc response : {}", paymentMethodFindAllResponse.toString());

    return new CommonResponse<>(
      "200",
      "성공",
      new ResponsePaymentReady(
        paymentInfo.getPaymentId(),
        paymentInfo.getDi(),
        jwtUtil.generate(new CustomerVo(customer)),
        paymentMethodFindAllResponse.getPaymentMethodList().stream().map(data -> {
          PaymentMethodType method;

          if (data.getMethod().equals("CARD"))
          {
            method = PaymentMethodType.CARD;
          }
          else
          {
            throw new KnownException(ExceptionEnum.UNKNOWN_PAYMENT_METHOD);
          }

          return new ResponsePaymentMethod(
            data.getId(),
            method,
            data.getNickName()
          );
        }).toList()
      )
    );
  }

  @Override
  @Transactional
  public CommonResponse<ResponsePaymentRequest> requestPayment(
    final String di,
    final RequestPaymentRequest requestPaymentReady
  )
  {
    /**
     * 1. 결제 정보 조회
     * 2. 결제 토큰 발급
     * 3. 결제 정보 업데이트
     * 4. 결제 요청
     */

    PaymentInfoEntity paymentInfo = paymentInfoRepository.findByDiAndPaymentId(di, requestPaymentReady.getPaymentId());

    if (paymentInfo == null)
    {
      throw new KnownException(ExceptionEnum.NOT_EXIST_PAYMENT);
    }

    if (!requestPaymentReady.validate(paymentInfo))
    {
      throw new KnownException(ExceptionEnum.INVALID_PAYMENT_REQUEST);
    }

    TokenCreateV1.Response tokenCreateResponse = tokenCreateBlockingStub.create(
      TokenCreateV1.Request
        .newBuilder()
        .setDi(di)
        .setMid(requestPaymentReady.getMid())
        .setPaymentMethodId(requestPaymentReady.getPaymentMethodId())
        .setPaymentId(requestPaymentReady.getPaymentId())
        .setExpectAmount(requestPaymentReady.getAmount().longValue())
        .build()
    );

    log.debug("grpc response : {}", tokenCreateResponse.toString());

    paymentInfo.setRequestedAt(LocalDateTime.now());
    paymentInfo.setType(EASY_PAYMENT);
    paymentInfo.setStatus(PAYMENT_STATUS_REQUEST);
    paymentInfo.setInstallmentMonth(requestPaymentReady.getInstallmentMonth());
    paymentInfo.setCallbackUrl(requestPaymentReady.getCallbackUrl());

    paymentInfoRepository.save(paymentInfo);

    PaymentLedgerEntity paymentLedger = new PaymentLedgerEntity(
      paymentInfo,
      EASY_PAYMENT,
      requestPaymentReady.getPaymentMethodId(),
      tokenCreateResponse.getToken()
    );

    paymentLedgerRepository.save(paymentLedger);

    RequestPaymentApproval requestPaymentApproval = new RequestPaymentApproval(
      requestPaymentReady,
      paymentLedger.getLedgerId(),
      tokenCreateResponse.getToken()
    );

    kafkaTemplate.send("paymentApprovalRequest", JsonUtil.toJson(requestPaymentApproval));

    return new CommonResponse<>(
      "200",
      "성공",
      new ResponsePaymentRequest(
        paymentInfo.getPaymentId(),
        paymentInfo.getDi(),
        paymentLedger.getMethodId(),
        paymentInfo.getCallbackUrl()
      )
    );
  }

  @Override
  @KafkaListener(topics = "paymentApprovalRequest")
  @Transactional
  public void approvePaymentRequest(String request)
  {
    RequestPaymentApproval requestPaymentApproval = (RequestPaymentApproval) JsonUtil.fromJson(request, RequestPaymentApproval.class);

    RestClient restClient = RestClient.create();

    ResponseEntity<String> response = restClient.post()
      .uri("http://localhost:8082/api/approval/v1")
      .body(requestPaymentApproval)
      .retrieve()
      .toEntity(String.class);

    log.debug("승인 결과 : {}", response);

    if (response.getStatusCode() == HttpStatusCode.valueOf(200)
      && !response.getBody().isBlank()
    )
    {
      PaymentInfoEntity paymentInfoEntity = paymentInfoRepository.findById(requestPaymentApproval.getPaymentId())
        .orElseThrow(() -> new KnownException(ExceptionEnum.NOT_EXIST_PAYMENT));

      paymentInfoEntity.setStatus("APPROVED");
      paymentInfoEntity.setApprovedAt(LocalDateTime.now());
      paymentInfoEntity.setBalance(requestPaymentApproval.getAmount());
      paymentInfoEntity.setUpdateAt(LocalDateTime.now());

      paymentInfoRepository.save(paymentInfoEntity);

      PaymentLedgerEntity paymentLedgerEntity = paymentLedgerRepository.findById(requestPaymentApproval.getPaymentLedgerId())
        .orElseThrow(() -> new KnownException(ExceptionEnum.NOT_EXIST_PAYMENT_LEDGER));

      paymentLedgerEntity.setStatus("APPROVED");
      paymentLedgerEntity.setApprovalCode(response.getBody());

      paymentLedgerRepository.save(paymentLedgerEntity);
    }
    else {
      PaymentInfoEntity paymentInfoEntity = paymentInfoRepository.findById(requestPaymentApproval.getPaymentId())
        .orElseThrow(() -> new KnownException(ExceptionEnum.NOT_EXIST_PAYMENT));

      paymentInfoEntity.setStatus("REJECTED");
      paymentInfoEntity.setUpdateAt(LocalDateTime.now());

      paymentInfoRepository.save(paymentInfoEntity);

      PaymentLedgerEntity paymentLedgerEntity = paymentLedgerRepository.findById(requestPaymentApproval.getPaymentLedgerId())
        .orElseThrow(() -> new KnownException(ExceptionEnum.NOT_EXIST_PAYMENT_LEDGER));

      paymentLedgerEntity.setStatus("REJECTED");

      paymentLedgerRepository.save(paymentLedgerEntity);
    }
    //Callback 호출로직 추가

  }
}
