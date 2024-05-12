package song.pg.payment.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig
{
  @Bean
  public Flyway flyway(DataSource dataSource)
  {
    Flyway flyway = Flyway.configure()
      .dataSource(dataSource)
      .cleanDisabled(false)
      .locations("classpath:db/migration")
      .load();

    flyway.clean();
    flyway.migrate();

    return flyway;
  }
}
