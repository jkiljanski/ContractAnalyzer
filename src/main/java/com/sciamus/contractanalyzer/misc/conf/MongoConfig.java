package com.sciamus.contractanalyzer.misc.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.sciamus.contractanalyzer.infrastructure.port")
public class MongoConfig {
}
