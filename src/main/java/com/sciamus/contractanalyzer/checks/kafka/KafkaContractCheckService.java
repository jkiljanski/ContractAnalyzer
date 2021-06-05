package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.checks.CheckNotFoundException;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KafkaContractCheckService {

    private final List<KafkaContractCheck> kafkaChecks;

    public KafkaContractCheckService(List<KafkaContractCheck> kafkaChecks) {
        this.kafkaChecks = kafkaChecks;
    }


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
