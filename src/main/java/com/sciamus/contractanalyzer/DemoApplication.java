
package com.sciamus.contractanalyzer;

import io.vavr.jackson.datatype.VavrModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;



@SpringBootApplication()
@EnableFeignClients
@EnableMongoRepositories(basePackages = "com.sciamus.contractanalyzer.reporting")

//(basePackages = "com.sciamus.contractanalyzer.reporting")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    VavrModule vavrModule() {
        return new VavrModule();
    }
}
