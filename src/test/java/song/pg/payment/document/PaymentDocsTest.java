package song.pg.payment.document;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import song.pg.payment.config.security.CustomerUserDetails;
import song.pg.payment.config.security.MerchantUserDetails;
import song.pg.payment.models.customer.CustomerEntity;
import song.pg.payment.models.merchant.MerchantInfoEntity;
import song.pg.payment.models.merchant.MerchantInfoVo;
import song.pg.payment.models.payment.PaymentInfoEntity;
import song.pg.payment.models.payment.ready.RequestPaymentReady;
import song.pg.payment.models.payment.request.RequestPaymentRequest;
import song.pg.payment.repository.CustomerRepository;
import song.pg.payment.repository.MerchantInfoRepository;
import song.pg.payment.repository.PaymentInfoRepository;
import song.pg.payment.utils.JsonUtil;

import java.math.BigDecimal;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class PaymentDocsTest
{
  private MockMvc mockMvc;

  @Autowired
  private MerchantInfoRepository merchantInfoRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private PaymentInfoRepository paymentInfoRepository;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation)
  {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
      .apply(documentationConfiguration(restDocumentation))
      .build();
  }

  @Test
  @DisplayName("카드 정보 등록")
  void registerCardInfo() throws Exception
  {
    CustomerEntity entity = new CustomerEntity(
      "wEi9oYSuekQGxT9MV4rKHG4CO+Zrp+onhLIIuembI8jx/0PLF5Ne3oMBxvUFlN4UmsgjeNErZfmpCVUFHsv8nq==",
      "7210c942fb534cacaa2553446da1d7ea"
    );

    customerRepository.save(entity);
    UserDetails user = new CustomerUserDetails(entity.getDi(), entity.getMid());
    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);

    this.mockMvc.perform(
        post("/api/payment/method/card/v1")
          .contentType(MediaType.APPLICATION_JSON)
          .header("Authorization", "Bearer YWJjZDoxMjM0")
          .content(
            """
              {
                "cardNumber1": "1234",
                "cardNumber2": "1234",
                "cardNumber3": "xE7Da6cUvZs5AniHgfANBg==",
                "cardNumber4": "xE7Da6cUvZs5AniHgfANBg==",
                "expireYear": 2023,
                "expireMonth": 12,
                "cvc": 123,
                "password": 12,
                "cardHolderName": "홍길동",
                "nickName": "홍길동카드"
              }
              """
          )
          .with(user(user))
          .with(authentication(authentication))
          .with(securityContext(securityContext))
      )
      .andExpect(status().isOk())
      .andDo(
        document("payment/method/card/create",
          resource(
            ResourceSnippetParameters.builder()
              .description("카드 정보 등록")
              .requestHeaders(
                headerWithName("Authorization").description("Bearer AccessToken")
              )
              .requestFields(
                fieldWithPath("cardNumber1").description("카드 번호 첫번째 4자리").type(STRING),
                fieldWithPath("cardNumber2").description("카드 번호 두번째 4자리").type(STRING),
                fieldWithPath("cardNumber3").description("카드 번호 세번째 4자리(암호화)").type(STRING),
                fieldWithPath("cardNumber4").description("카드 번호 네번째 4자리(암호화)").type(STRING),
                fieldWithPath("expireYear").description("카드 만료 연도").type(NUMBER),
                fieldWithPath("expireMonth").description("카드 만료 월").type(NUMBER),
                fieldWithPath("cvc").description("카드 CVC").type(NUMBER),
                fieldWithPath("password").description("비밀번호 앞 2글자").type(NUMBER),
                fieldWithPath("cardHolderName").description("카드 소유주 이름").type(STRING),
                fieldWithPath("nickName").description("결제수단 별칭").type(STRING)
              )
              .responseFields(
                fieldWithPath("code").description("응답코드").type(STRING),
                fieldWithPath("message").description("응답메세지").type(STRING),
                fieldWithPath("data").description("등록 결제수단 정보").type(OBJECT),
                fieldWithPath("data.id").description("결제수단ID").type(STRING),
                fieldWithPath("data.method").description("결제수단종류").type(STRING)
                  .attributes(key("constraints").value("CARD")),
                fieldWithPath("data.nickName").description("결제수단 별칭").type(STRING)
              )
              .build()
          )
        )
      );
  }

  @Test
  @DisplayName("결제준비 요청")
  void PaymentReady() throws Exception
  {
    MerchantInfoEntity entity = merchantInfoRepository.findById("7210c942fb534cacaa2553446da1d7ea").orElseThrow();
    UserDetails user = new MerchantUserDetails(new MerchantInfoVo(entity));
    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);

    this.mockMvc.perform(
        post("/api/payment/ready/v1")
          .contentType(MediaType.APPLICATION_JSON)
          .header("Authorization", "Basic YWJjZDoxMjM0")
          .header("X-CUSTOMER-ID", "wEi9oYSuekQGxT9MV4rKHG4CO+Zrp+onhLIIuembI8jx/0PLF5Ne3oMBxvUFlN4UmsgjeNErZfmpCVUFHsv8nq==")
          .content(
            """
              {
                "mid": "7210c942fb534cacaa2553446da1d7ea",
                "amount": 1000000,
                "taxFreeAmount": 0,
                "orderId": "testOrder20240513_1",
                "orderName": "아이폰15 Pro Max 512GB"
              }
              """
          )
          .with(user(user))
          .with(authentication(authentication))
          .with(securityContext(securityContext))

      )
      .andExpect(status().isOk())
      .andDo(
        document("payment/ready",
          resource(
            ResourceSnippetParameters.builder()
              .description("결제준비 요청")
              .requestHeaders(
                headerWithName("Authorization").description("Basic Base64(가맹점ID:가맹점시크릿키)"),
                headerWithName("X-CUSTOMER-ID").description("사용자CI")
              )
              .requestFields(
                fieldWithPath("mid").description("가맹점ID").type(STRING),
                fieldWithPath("amount").description("결제금액").type(NUMBER),
                fieldWithPath("taxFreeAmount").description("비과세금액").type(NUMBER),
                fieldWithPath("orderId").description("주문번호").type(STRING),
                fieldWithPath("orderName").description("구매상품명").type(STRING)
              )
              .responseFields(
                fieldWithPath("code").description("응답코드").type(STRING),
                fieldWithPath("message").description("응답메세지").type(STRING),
                fieldWithPath("data").description("응답결과").type(OBJECT),
                fieldWithPath("data.paymentId").description("결제ID").type(STRING),
                fieldWithPath("data.customerDi").description("사용자DI").type(STRING),
                fieldWithPath("data.paymentMethod").description("결제정보(등록간편결제)").type(ARRAY),
                fieldWithPath("data.paymentMethod[].id").description("결제수단ID").type(STRING),
                fieldWithPath("data.paymentMethod[].method").description("결제수단종류").type(STRING)
                  .attributes(key("constraints").value("CARD")),
                fieldWithPath("data.paymentMethod[].nickName").description("결제수단 별칭").type(STRING),
                fieldWithPath("data.accessToken").description("액세스토큰").type(STRING)
              )
              .build()
          )
        )
      );
  }

  @Test
  @DisplayName("결제 요청(간편)")
  void PaymentRequest() throws Exception
  {
    CustomerEntity entity = new CustomerEntity(
      "wEi9oYSuekQGxT9MV4rKHG4CO+Zrp+onhLIIuembI8jx/0PLF5Ne3oMBxvUFlN4UmsgjeNErZfmpCVUFHsv8nq==",
      "7210c942fb534cacaa2553446da1d7ea"
    );

    customerRepository.save(entity);
    UserDetails user = new CustomerUserDetails(entity.getDi(), entity.getMid());
    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    SecurityContext securityContext = org.springframework.security.core.context.SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);

    RequestPaymentReady ready = new RequestPaymentReady(
      "7210c942fb534cacaa2553446da1d7ea",
      BigDecimal.valueOf(1000000),
      BigDecimal.ZERO,
      "testOrder20240513_1",
      "아이폰15 Pro Max 512GB"
    );

    PaymentInfoEntity paymentInfoEntity = new PaymentInfoEntity(
      "3ed70a83292af51f87106ceca85eaaa2d8812f92a1c3e93166b3d52758da4431",
      ready
    );


    paymentInfoRepository.save(paymentInfoEntity);

    RequestPaymentRequest req = new RequestPaymentRequest(
      paymentInfoEntity.getPaymentId(),
      paymentInfoEntity.getMid(),
      paymentInfoEntity.getTotalAmount(),
      BigDecimal.ZERO,
      paymentInfoEntity.getOrderId(),
      paymentInfoEntity.getOrderName(),
      "fe4df140f30b4605bfea7b9d912a16cd",
      0,
      "http://customer.co.kr/approval/v1"
    );

    this.mockMvc.perform(
        post("/api/payment/request/easy/v1")
          .contentType(MediaType.APPLICATION_JSON)
          .header("Authorization", "Bearer YWJjZDoxMjM0")
          .content(JsonUtil.toJson(req))
      )
      .andExpect(status().isOk())
      .andDo(
        document("payment/request/easy",
          resource(
            ResourceSnippetParameters.builder()
              .description("결제 요청")
              .requestHeaders(
                headerWithName("Authorization").description("Bearer AccessToken")
              )
              .requestFields(
                fieldWithPath("paymentId").description("결제ID").type(STRING),
                fieldWithPath("mid").description("가맹점ID").type(STRING),
                fieldWithPath("amount").description("결제금액").type(NUMBER),
                fieldWithPath("taxFreeAmount").description("비과세금액").type(NUMBER),
                fieldWithPath("orderId").description("주문번호").type(STRING),
                fieldWithPath("orderName").description("구매상품명").type(STRING),
                fieldWithPath("paymentMethodId").description("결제수단ID").type(STRING),
                fieldWithPath("installmentMonth").description("할부개월수").type(NUMBER).optional(),
                fieldWithPath("callbackUrl").description("콜백URL").type(STRING)
              )
              .responseFields(
                fieldWithPath("code").description("응답코드").type(STRING),
                fieldWithPath("message").description("응답메세지").type(STRING),
                fieldWithPath("data").description("응답결과").type(OBJECT),
                fieldWithPath("data.paymentId").description("결제ID").type(STRING),
                fieldWithPath("data.customerDi").description("사용자DI").type(STRING),
                fieldWithPath("data.paymentMethodId").description("결제수단ID").type(STRING),
                fieldWithPath("data.callbackUrl").description("콜백URL").type(STRING)
              )
              .build()
          )
        )
      );
  }
}
