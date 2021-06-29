package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.domain.checks.queues.KafkaContractCheckService;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KafkaCheckController {

    @Autowired
    private KafkaContractCheckService kafkaContractCheckService;

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