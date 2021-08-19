package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.domain.checks.reports.ReportNotFoundException;
import com.sciamus.contractanalyzer.infrastructure.port.ReportInfrastructureDTO;
import com.sciamus.contractanalyzer.infrastructure.port.ReportPersistancePort;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MongoReportPersistenceAdapter implements ReportPersistancePort {

    private final ReportDocumentMapper reportDocumentMapper;

    private final MongoReportsRepository mongoReportsRepository;

    private final ReportIdGenerator reportIdGenerator;

    public MongoReportPersistenceAdapter(ReportDocumentMapper reportDocumentMapper, MongoReportsRepository mongoReportsRepository, ReportIdGenerator reportIdGenerator) {
        this.reportDocumentMapper = reportDocumentMapper;
        this.mongoReportsRepository = mongoReportsRepository;
        this.reportIdGenerator = reportIdGenerator;
    }

    @Override
    public ReportInfrastructureDTO save(ReportInfrastructureDTO report) {

        ReportDocument reportDocument = reportDocumentMapper.mapToDocument(report);
        reportDocument.id = reportIdGenerator.getNextID();
        ReportDocument saved = mongoReportsRepository.save(reportDocument);
        return reportDocumentMapper.mapFromDocument(saved);

    }
    @Override
    public ReportInfrastructureDTO findById(String id) {

       return reportDocumentMapper.mapFromDocument(mongoReportsRepository.findById(id).orElseThrow(() -> new ReportNotFoundException("" + id)));

    }
    @Override
    public List<ReportInfrastructureDTO> findAll() {
        return mongoReportsRepository.findAll().stream().map(reportDocumentMapper::mapFromDocument).collect(Collectors.toList());
    }

    @Override
    public List<ReportInfrastructureDTO> findAll(int pageSize) {
        return mongoReportsRepository.findAll(Pageable.ofSize(pageSize)).stream().map(reportDocumentMapper::mapFromDocument).collect(Collectors.toList());
    }

    public List<ReportInfrastructureDTO> findAllByTimestampBetweenAndNameOfCheckAndResultAndUserNameAndReportBodyContaining(LocalDateTime from, LocalDateTime to, String name, String result, String user, String reportBody) {
       return mongoReportsRepository.findAllByTimestampBetweenAndNameOfCheckAndResultAndUserNameAndReportBodyContaining(from, to, name, result, user, reportBody)
               .stream()
               .map(reportDocumentMapper::mapFromDocument).collect(Collectors.toList());
    }




}
