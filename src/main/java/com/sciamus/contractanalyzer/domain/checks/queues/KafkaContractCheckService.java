package com.sciamus.contractanalyzer.domain.checks.queues;

import com.sciamus.contractanalyzer.domain.checks.queues.kafka.KafkaContractCheck;
import com.sciamus.contractanalyzer.domain.exception.CheckNotFoundException;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class KafkaContractCheckService {

    @Autowired
    private List<KafkaContractCheck> kafkaChecks;

    public CheckReport runKafkaCheck (String incomingTopic, String outgoingTopic, String host, String port, String name) {

        KafkaContractCheck kafkaContractCheck = kafkaChecks.stream()
                .filter(s->s.getName().equals(name))
                .findFirst().orElseThrow(()-> new CheckNotFoundException(name));

        return kafkaContractCheck.run(incomingTopic, outgoingTopic, host, port);

    }


    public List<String> getAllChecks() {
        return kafkaChecks.stream().map(KafkaContractCheck::getName).collect(Collectors.toList());
    }




}
