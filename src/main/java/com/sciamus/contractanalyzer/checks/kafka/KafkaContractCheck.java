package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.reporting.checks.CheckReport;

public interface KafkaContractCheck {

    CheckReport run(String incomingTopic, String outgoingTopic, String host, String port);


    String getName();


}
