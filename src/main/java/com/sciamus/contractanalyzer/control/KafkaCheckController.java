package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.checks.kafka.KafkaContractCheckService;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController


public class KafkaCheckController {

    private final KafkaContractCheckService kafkaContractCheckService;


    public KafkaCheckController(KafkaContractCheckService kafkaContractCheckService) {
        this.kafkaContractCheckService = kafkaContractCheckService;
    }

    //add parameters
    @PostMapping("/kafkaCheck/{name}/run")
    CheckReport runKafka(@PathVariable("name") String name,
                         @RequestParam("incomingTopic") String firstTopic,
                         @RequestParam("outgoingTopic") String secondTopic,
                         @RequestParam("host") String host,
                         @RequestParam("port") String port) {

        return kafkaContractCheckService.runKafkaCheck(firstTopic, secondTopic, host, port,name);
    }

    @GetMapping("/kafkaCheck/")
    List<String> getAllChecks() {
       return kafkaContractCheckService.getAllChecks();
    }


}
//?firstTopic={firstTopic}&secondTopic={secondTopic}&host={host}&port={port}