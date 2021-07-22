package com.sciamus.contractanalyzer.infrastructure;

import com.sciamus.contractanalyzer.infrastructure.adapter.MongoConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MongoConfig.class})
public class InfrastructureConfig {
}
