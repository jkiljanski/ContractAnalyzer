package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.checks.kafka.KafkaContractCheck;
import com.sciamus.contractanalyzer.checks.kafka.KafkaContractService;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController


public class KafkaCheckController {


    KafkaContractService kafkaContractService;



//add parameters
    @PostMapping("/kafkaContractCheck/pingpong?firstTopic={firstTopic}&secondTopic={secondTopic}&host={host}&port={port}")
    CheckReport runKafka(@Param("firstTopic") String firstTopic, @Param("secondTopic") String secondTopic, @Param("host") String host, @Param("port") String port) {

    return kafkaContractService.runKafkaCheck(firstTopic, secondTopic, host, port);

    }

}
