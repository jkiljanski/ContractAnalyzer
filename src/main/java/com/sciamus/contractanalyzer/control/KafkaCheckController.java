package com.sciamus.contractanalyzer.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaCheckController {

//add parameters
    @GetMapping("/kafkaContractCheck/pingpong")
    CheckReportDTO runKafka(String firstTopic, String secondTopic) {

    return null;

    }


}
