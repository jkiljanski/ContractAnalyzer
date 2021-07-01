package config;

import com.sciamus.contractanalyzer.AppConfig;
import com.sciamus.contractanalyzer.domain.checks.rest.config.RestChecksConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Import({AppConfig.class, RestChecksConfig.class})
@Configuration
public class TestConfig {




}
