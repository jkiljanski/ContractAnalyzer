package com.sciamus.contractanalyzer.domain.contracts;

import com.sciamus.contractanalyzer.domain.contracts.rest.RestContractsConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({RestContractsConfig.class})
@Configuration
public class ContractsDomainConfig {


}

