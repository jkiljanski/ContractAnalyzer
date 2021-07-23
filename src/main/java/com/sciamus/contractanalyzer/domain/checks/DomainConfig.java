package com.sciamus.contractanalyzer.domain.checks;


import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportConfig;
import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaConfig;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportsConfig;
import com.sciamus.contractanalyzer.domain.checks.rest.RestChecksConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({RestChecksConfig.class, ReportsConfig.class, KafkaConfig.class, AggregatedReportConfig.class})
@Configuration
public class DomainConfig {


}

