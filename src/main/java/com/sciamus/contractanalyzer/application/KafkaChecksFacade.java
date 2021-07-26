package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.application.mapper.ReportMapper;
import com.sciamus.contractanalyzer.domain.checks.queues.KafkaCheckService;

import java.util.List;

public class KafkaChecksFacade {

    private final KafkaCheckService kafkaCheckService;
    private  final ReportMapper reportMapper;

    public KafkaChecksFacade(KafkaCheckService kafkaCheckService, ReportMapper reportMapper) {
        this.kafkaCheckService = kafkaCheckService;
        this.reportMapper = reportMapper;
    }


    public ReportDTO runKafkaCheck(String firstTopic, String secondTopic, String host, String port, String name) {
        return  reportMapper.mapToDTO(kafkaCheckService.runKafkaCheck(firstTopic,secondTopic,host,port, name));
    }

    public List<String> getAllChecks() {
        return kafkaCheckService.getAllChecks();
    }
}
