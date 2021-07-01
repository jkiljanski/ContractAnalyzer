package com.sciamus.contractanalyzer.domain.checks.queues.kafka;


import com.sciamus.contractanalyzer.domain.checks.reports.CheckReport;

public interface KafkaContractCheck {

    CheckReport run(String incomingTopic, String outgoingTopic, String host, String port);

    String getName();


}
