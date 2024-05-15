package song.pg.payment.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class JsonUtil
{
  public static String toJson(final Object object)
  {
    try
    {
      ObjectMapper objectMapper = new ObjectMapper();
      StringWriter stringWriter = new StringWriter();
      objectMapper.writeValue(stringWriter, object);

      return stringWriter.toString();
    }
    catch (IOException e)
    {
      throw new KnownException(ExceptionEnum.INVALID_TO_JSON_FORMAT);
    }

  }

  public static Object fromJson(final String json, final Class<?> clazz)
  {
    try
    {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, clazz);
    }
    catch (IOException e)
    {
      throw new KnownException(ExceptionEnum.INVALID_JSON_FORMAT);
    }
  }
}
