package com.sciamus.contractanalyzer;

import com.sciamus.contractanalyzer.application.ApplicationConfig;
import com.sciamus.contractanalyzer.domain.checks.DomainConfig;
import com.sciamus.contractanalyzer.domain.contracts.ContractsDomainConfig;
import com.sciamus.contractanalyzer.infrastructure.InfrastructureConfig;
import com.sciamus.contractanalyzer.misc.conf.SecurityConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@SpringBootConfiguration
@EnableConfigurationProperties
@ComponentScan("com.sciamus.contractanalyzer.interfaces.rest")

@Import({SecurityConfig.class, DomainConfig.class, InfrastructureConfig.class, ApplicationConfig.class, ContractsDomainConfig.class})
public class GlobalConfig {


}
