package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.domain.checks.queues.KafkaCheckService;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KafkaCheckController {

    //TODO: fix me: this cannot use domain object directly -> it always must got through application layer
    private final KafkaCheckService kafkaCheckService;

    @Autowired
    public KafkaCheckController(KafkaCheckService kafkaCheckService) {
        this.kafkaCheckService = kafkaCheckService;
    }

    //add parameters
    @PostMapping("/kafkaCheck/{name}/run")
    Report runKafka(@PathVariable("name") String name,
                    @RequestParam("incomingTopic") String firstTopic,
                    @RequestParam("outgoingTopic") String secondTopic,
                    @RequestParam("host") String host,
                    @RequestParam("port") String port) {

        return kafkaCheckService.runKafkaCheck(firstTopic, secondTopic, host, port,name);
    }

    @GetMapping("/kafkaCheck/")
    List<String> getAllChecks() {
       return kafkaCheckService.getAllChecks();
    }


}
//?firstTopic={firstTopic}&secondTopic={secondTopic}&host={host}&port={port}