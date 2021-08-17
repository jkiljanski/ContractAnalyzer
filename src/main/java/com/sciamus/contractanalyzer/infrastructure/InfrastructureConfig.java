package com.sciamus.contractanalyzer.infrastructure;

import com.sciamus.contractanalyzer.infrastructure.adapter.mongo.MongoAdapterConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MongoAdapterConfig.class})
public class InfrastructureConfig {



}
