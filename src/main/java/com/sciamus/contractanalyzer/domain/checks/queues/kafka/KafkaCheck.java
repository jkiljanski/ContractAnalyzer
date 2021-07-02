package com.sciamus.contractanalyzer.domain.checks.queues.kafka;


import com.sciamus.contractanalyzer.domain.checks.reports.Report;

public interface KafkaCheck {

    Report run(String incomingTopic, String outgoingTopic, String host, String port);

    String getName();


}
