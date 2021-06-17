package com.sciamus.contractanalyzer.domain.checks;

import com.sciamus.contractanalyzer.domain.checks.dummy.DummyRestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.explode.ExplodeCheck;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChecksConfig {

    @Bean
    public RestContractCheck dummyRestContractCheck(){
        return new DummyRestContractCheck();
    }

    @Bean
    public RestContractCheck explodeCheck(){
        return new ExplodeCheck();
    }
}
