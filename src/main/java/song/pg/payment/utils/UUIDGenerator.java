package song.pg.payment.utils;

public class UUIDGenerator
{
  static public String generateUUID()
  {
    return java.util.UUID.randomUUID().toString().replace("-", "");
  }

  static public String generateUUID(String name)
  {
    return java.util.UUID.fromString(name).toString().replace("-", "");
  }
}
