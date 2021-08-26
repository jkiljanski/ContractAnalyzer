package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.application.mapper.report.ReportViewMapper;
import com.sciamus.contractanalyzer.domain.checks.queues.KafkaCheckService;

import java.util.List;

public class KafkaChecksFacade {

    private final KafkaCheckService kafkaCheckService;
    private  final ReportViewMapper reportViewMapper;

    public KafkaChecksFacade(KafkaCheckService kafkaCheckService, ReportViewMapper reportViewMapper) {
        this.kafkaCheckService = kafkaCheckService;
        this.reportViewMapper = reportViewMapper;
    }


    public ReportViewDTO runKafkaCheck(String firstTopic, String secondTopic, String host, String port, String name) {
        return  reportViewMapper.mapToDTO(kafkaCheckService.runKafkaCheck(firstTopic,secondTopic,host,port, name));
    }

    public List<String> getAllChecks() {
        return kafkaCheckService.getAllChecks();
    }
}
