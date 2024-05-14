package song.pg.payment.api.payment.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import song.pg.payment.api.payment.PaymentService;
import song.pg.payment.method.findAll.v1.proto.MethodFindAllV1;
import song.pg.payment.method.findAll.v1.proto.PaymentMethodFindAllServiceGrpc;
import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.customer.CustomerEntity;
import song.pg.payment.models.payment.method.PaymentMethodType;
import song.pg.payment.models.payment.method.ResponsePaymentMethod;
import song.pg.payment.models.payment.ready.PaymentInfoEntity;
import song.pg.payment.models.payment.ready.RequestPaymentReady;
import song.pg.payment.models.payment.ready.ResponsePaymentReady;
import song.pg.payment.repository.CustomerRepository;
import song.pg.payment.repository.PaymentInfoRepository;
import song.pg.payment.utils.ExceptionEnum;
import song.pg.payment.utils.JwtUtil;
import song.pg.payment.utils.KnownException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService
{
  private final CustomerRepository customerRepository;
  private final PaymentInfoRepository paymentInfoRepository;
  private final JwtUtil jwtUtil;
  private final KafkaTemplate<String, String> kafkaTemplate;

  @GrpcClient("token")
  private PaymentMethodFindAllServiceGrpc.PaymentMethodFindAllServiceBlockingStub paymentMethodFindAllBlockingStub;

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
        jwtUtil.generate(),
        paymentMethodFindAllResponse.getPaymentMethodList().stream().map(data -> {
          PaymentMethodType method = null;

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
}
