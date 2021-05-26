package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.checks.kafka.KafkaContractService;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController


public class KafkaCheckController {

    private final KafkaContractService kafkaContractService;


    public KafkaCheckController(KafkaContractService kafkaContractService) {
        this.kafkaContractService = kafkaContractService;
    }

    //add parameters
    @GetMapping("/kafkaContractCheck/pingpong")
    CheckReport runKafka(@RequestParam("firstTopic") String firstTopic, @RequestParam("secondTopic") String secondTopic, @RequestParam("host") String host, @RequestParam("port") String port) {

        return kafkaContractService.runKafkaCheck(firstTopic, secondTopic, host, port);

    }

}
//?firstTopic={firstTopic}&secondTopic={secondTopic}&host={host}&port={port}