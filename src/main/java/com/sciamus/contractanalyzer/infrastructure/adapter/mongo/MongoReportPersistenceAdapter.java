package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.application.ReportFilterParameters;
import com.sciamus.contractanalyzer.application.mapper.queries.ReportFilterParametersMapper;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportNotFoundException;
import com.sciamus.contractanalyzer.infrastructure.port.ReportFilterParametersInfrastructureDTO;
import com.sciamus.contractanalyzer.infrastructure.port.ReportInfrastructureDTO;
import com.sciamus.contractanalyzer.infrastructure.port.ReportPersistancePort;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.stream.Collectors;

public class MongoReportPersistenceAdapter implements ReportPersistancePort {

    private final ReportDocumentMapper reportDocumentMapper;

    private final MongoReportsRepository mongoReportsRepository;

    private final ReportIdGenerator reportIdGenerator;

    private final MongoTemplate mongoTemplate;

    private final ReportFilterParametersMapper reportFilterParametersMapper;


    public MongoReportPersistenceAdapter(ReportDocumentMapper reportDocumentMapper, MongoReportsRepository mongoReportsRepository, ReportIdGenerator reportIdGenerator, MongoTemplate mongoTemplate, ReportFilterParametersMapper reportFilterParametersMapper) {
        this.reportDocumentMapper = reportDocumentMapper;
        this.mongoReportsRepository = mongoReportsRepository;
        this.reportIdGenerator = reportIdGenerator;
        this.mongoTemplate = mongoTemplate;
        this.reportFilterParametersMapper = reportFilterParametersMapper;
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
    public Page<ReportInfrastructureDTO> findAll(int pageNumber) {

        Page<ReportDocument> page = mongoReportsRepository.findAll(PageRequest.of(pageNumber, 10));

        return page.map(reportDocumentMapper::mapFromDocument);

    }

    @Override
    public Page<ReportInfrastructureDTO> findAll(int pageNumber, String sortingProperty, String sortingOrder) {


        Page<ReportDocument> page = mongoReportsRepository.findAll(PageRequest.of(pageNumber, 10, Sort.Direction.fromString(sortingOrder), sortingProperty));

        return page.map(reportDocumentMapper::mapFromDocument);
    }

    @Override
    public Page<ReportInfrastructureDTO> findAll(ReportFilterParameters reportFilterParameters, int pageNumber) {

        MongoQueryBuilder mongoQueryBuilder = new MongoQueryBuilder();

        Pageable pageable;

        if (reportFilterParameters.sortingOrder != null && reportFilterParameters.sortingProperty != null && !reportFilterParameters.sortingOrder.isBlank() && !reportFilterParameters.sortingProperty.isBlank()) {
             pageable = PageRequest.of(pageNumber, 10, Sort.Direction.fromString(reportFilterParameters.sortingOrder), reportFilterParameters.sortingProperty);
        } else {
             pageable = PageRequest.of(pageNumber, 10);
        }

        ReportFilterParametersInfrastructureDTO reportFilterParametersInfrastructureDTO = reportFilterParametersMapper.mapToDTO(reportFilterParameters);

        Query pagedQuery = mongoQueryBuilder.buildQuery(reportFilterParametersInfrastructureDTO).with(pageable);

        Query unpagedQueryForCountingPurposes = mongoQueryBuilder.buildQuery(reportFilterParametersInfrastructureDTO);

        List<ReportDocument> reportDocumentList = mongoTemplate.find(pagedQuery, ReportDocument.class, "checkReports");

        long count = mongoTemplate.count(unpagedQueryForCountingPurposes, ReportDocument.class, "checkReports");


        Page<ReportDocument> page = new PageImpl<>(reportDocumentList, pageable, count);

        return page.map(reportDocumentMapper::mapFromDocument);


    }
}
