package com.sciamus.contractanalyzer.checks.kafka;

import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.springframework.stereotype.Service;

@Service
public class KafkaContractService {

    private final KafkaContractCheck kafkaPingPongCheck;

    public KafkaContractService(KafkaContractCheck kafkaPingPongCheck) {
        this.kafkaPingPongCheck = kafkaPingPongCheck;
    }

    CheckReport runKafkaCheck () {

//        kafkaPingPongCheck.run()
        return  null;
    }


}
