package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.domain.checks.reports.ReportNotFoundException;
import com.sciamus.contractanalyzer.infrastructure.port.ReportInfrastructureDTO;
import com.sciamus.contractanalyzer.infrastructure.port.ReportPersistancePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
    public Page<ReportInfrastructureDTO> findAll(int pageNumber) {

        Page<ReportDocument> page = mongoReportsRepository.findAll(PageRequest.of(pageNumber,10));

        return page.map(reportDocumentMapper::mapFromDocument);

//        return mongoReportsRepository.findAll(Pageable.ofSize(pageSize)).stream().map(reportDocumentMapper::mapFromDocument).collect(Collectors.toList());
    }

    @Override
    public Page<ReportInfrastructureDTO> findAll(int pageSize, String sortingProperty, String sortingOrder) {



        Page<ReportDocument> page = mongoReportsRepository.findAll(PageRequest.of(pageSize,10, Sort.Direction.fromString(sortingOrder), sortingProperty));

        return page.map(reportDocumentMapper::mapFromDocument);
    }
}
