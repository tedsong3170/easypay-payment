package song.pg.payment.api.payment.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import song.pg.payment.api.payment.PaymentService;
import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.customer.CustomerEntity;
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
public class PaymentServiceImpl implements PaymentService
{
  private final CustomerRepository customerRepository;
  private final PaymentInfoRepository paymentInfoRepository;
  private final JwtUtil jwtUtil;
  private final KafkaTemplate<String, String> kafkaTemplate;


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


    return new CommonResponse<ResponsePaymentReady>(
      "200",
      "성공",
      new ResponsePaymentReady(
        paymentInfo.getPaymentId(),
        paymentInfo.getDi(),
        jwtUtil.generate()
      )
    );
  }
}
