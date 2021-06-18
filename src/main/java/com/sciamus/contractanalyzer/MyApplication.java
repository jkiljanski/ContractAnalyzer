
package com.sciamus.contractanalyzer;

import com.sciamus.contractanalyzer.domain.checks.ChecksConfig;
import com.sciamus.contractanalyzer.domain.reporting.ReportingConfig;
import com.sciamus.contractanalyzer.domain.suites.SuitesConfig;
import com.sciamus.contractanalyzer.interfaces.rest.RestConfig;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication()
@EnableFeignClients
@EnableMongoRepositories(basePackages = "com.sciamus.contractanalyzer.domain")
@EnableConfigurationProperties
@Import(AppConfig.class)
@EnableSwagger2
//(basePackages = "com.sciamus.contractanalyzer.domain.reporting")
public class MyApplication {
    private static final Logger logger = LogManager.getLogger(MyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @Bean
    VavrModule vavrModule() {
        return new VavrModule();
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.sciamus.contractanalyzer")).build();
    }
}
