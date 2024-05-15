package song.pg.payment.api.method.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import song.pg.payment.api.method.PaymentMethodService;
import song.pg.payment.method.card.create.v1.proto.MethodCardCreateV1;
import song.pg.payment.method.card.create.v1.proto.PaymentMethodCardCreateServiceGrpc;
import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.payment.method.PaymentMethodType;
import song.pg.payment.models.payment.method.ResponsePaymentMethod;
import song.pg.payment.models.payment.method.card.RequestPaymentMethodCardRegister;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService
{
  @GrpcClient("token")
  private PaymentMethodCardCreateServiceGrpc.PaymentMethodCardCreateServiceBlockingStub paymentMethodCreateServiceBlockingStub;

  @Override
  public CommonResponse<ResponsePaymentMethod> registerCardInfo(
    final String di,
    final String mid,
    final RequestPaymentMethodCardRegister requestPaymentMethodCardRegister
  )
  {
    MethodCardCreateV1.Response cardCreateResponse = paymentMethodCreateServiceBlockingStub.createCardInfo(
      MethodCardCreateV1.Request.newBuilder()
        .setDi(di)
        .setMid(mid)
        .setCardInfo(
          MethodCardCreateV1.CardInfo.newBuilder()
            .setCardNumber1(requestPaymentMethodCardRegister.getCardNumber1())
            .setCardNumber2(requestPaymentMethodCardRegister.getCardNumber2())
            .setCardNumber3(requestPaymentMethodCardRegister.getCardNumber3())
            .setCardNumber4(requestPaymentMethodCardRegister.getCardNumber4())
            .setCardHolderName(requestPaymentMethodCardRegister.getCardHolderName())
            .setExpireMonth(requestPaymentMethodCardRegister.getExpireMonth())
            .setExpireYear(requestPaymentMethodCardRegister.getExpireYear())
            .setCvc(requestPaymentMethodCardRegister.getCvc())
            .setNickName(requestPaymentMethodCardRegister.getNickName())
            .build()
        )
        .build()
    );

    log.debug("카드 등록 결과: {}", cardCreateResponse);

    if (cardCreateResponse.getCode().equals("200"))
    {
      return new CommonResponse<ResponsePaymentMethod>(
        "200",
        "성공",
        ResponsePaymentMethod.builder()
        .id(cardCreateResponse.getId())
        .method(PaymentMethodType.CARD)
        .nickName(cardCreateResponse.getNickName())
        .build()
      );
    }
    else {
      return new CommonResponse<ResponsePaymentMethod>(
        cardCreateResponse.getCode(),
        cardCreateResponse.getMessage(),
        null
      );
    }
  }
}
