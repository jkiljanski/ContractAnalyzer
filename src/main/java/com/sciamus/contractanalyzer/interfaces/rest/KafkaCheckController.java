package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.application.KafkaChecksFacade;
import com.sciamus.contractanalyzer.application.ReportViewDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KafkaCheckController {

    private final KafkaChecksFacade kafkaChecksFacade;

    public KafkaCheckController(KafkaChecksFacade kafkaChecksFacade) {
        this.kafkaChecksFacade = kafkaChecksFacade;
    }

    //add parameters
    @PostMapping("/kafkaCheck/{name}/run")
    ReportViewDTO runKafka(@PathVariable("name") String name,
                           @RequestParam("incomingTopic") String firstTopic,
                           @RequestParam("outgoingTopic") String secondTopic,
                           @RequestParam("host") String host,
                           @RequestParam("port") String port) {

        return kafkaChecksFacade.runKafkaCheck(firstTopic, secondTopic, host, port,name);
    }

    @GetMapping("/kafkaCheck/")
    List<String> getAllChecks() {
       return kafkaChecksFacade.getAllChecks();
    }


}
//?firstTopic={firstTopic}&secondTopic={secondTopic}&host={host}&port={port}