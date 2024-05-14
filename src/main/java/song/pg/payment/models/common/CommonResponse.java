package song.pg.payment.models.common;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommonResponse<T> implements Serializable
{
  private static final long serialVersionUID = 6701859334541408297L;

  private String code;
  private String message;
  private T data;
}


