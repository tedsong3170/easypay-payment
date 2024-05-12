package song.pg.payment.document;

import com.epages.restdocs.apispec.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class PaymentDocsTest {
  private MockMvc mockMvc;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
      .apply(documentationConfiguration(restDocumentation))
      .build();
  }

  @Test
  @DisplayName("카드 정보 등록")
  void registerCardInfo() throws Exception {
    final ParameterDescriptorWithType[] requestFields = new ParameterDescriptorWithType[]{
      parameterWithName("cardNumber").description("카드 번호"),
    };


    this.mockMvc.perform(
      post("/api/payment/method/card/v1")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Basic YWJjZDoxMjM0")
        .header("X-CUSTOMER-ID", "wEi9oYSuekQGxT9MV4rKHG4CO+Zrp+onhLIIuembI8jx/0PLF5Ne3oMBxvUFlN4UmsgjeNErZfmpCVUFHsv8nq==")
        .content(
          """
          {
            "cardNumber": "1234567890123456",
            "expireYear": 2023,
            "expireMonth": 12,
            "cvc": 123,
            "password": 12,
            "cardHolderName": "홍길동"
          }
          """
        )
      )
      .andExpect(status().isOk())
      .andDo(
        document("payment/method/card/create",
          resource(
            ResourceSnippetParameters.builder()
              .description("카드 정보 등록")
              .requestHeaders(
                headerWithName("Authorization").description("Basic Base64(가맹점ID:가맹점시크릿키)"),
                headerWithName("X-CUSTOMER-ID").description("사용자ID")
              )
              .requestFields(
                fieldWithPath("cardNumber").description("카드 번호").type(JsonFieldType.STRING),
                fieldWithPath("expireYear").description("카드 만료 연도").type(JsonFieldType.NUMBER),
                fieldWithPath("expireMonth").description("카드 만료 월").type(JsonFieldType.NUMBER),
                fieldWithPath("cvc").description("카드 CVC").type(JsonFieldType.NUMBER),
                fieldWithPath("password").description("비밀번호 앞 2글자").type(JsonFieldType.NUMBER),
                fieldWithPath("cardHolderName").description("카드 소유주 이름").type(JsonFieldType.STRING)
              )
              .build()
          )
        )
      );
  }
}
