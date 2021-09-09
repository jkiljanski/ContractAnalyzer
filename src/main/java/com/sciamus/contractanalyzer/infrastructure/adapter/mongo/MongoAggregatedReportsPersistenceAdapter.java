package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.domain.checks.reports.ReportNotFoundException;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportInfrastructureDTO;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportPersistancePort;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class MongoAggregatedReportsPersistenceAdapter implements AggregatedReportPersistancePort {

    private final AggregatedReportIdGenerator aggregatedReportIdGenerator;

    private final MongoAggregatedReportsRepository mongoAggregatedReportsRepository;

    private final AggregatedReportDocumentMapper aggregatedReportDocumentMapper;

    public MongoAggregatedReportsPersistenceAdapter(AggregatedReportIdGenerator aggregatedReportIdGenerator, MongoAggregatedReportsRepository mongoAggregatedReportsRepository, AggregatedReportDocumentMapper aggregatedReportDocumentMapper) {
        this.aggregatedReportIdGenerator = aggregatedReportIdGenerator;
        this.mongoAggregatedReportsRepository = mongoAggregatedReportsRepository;
        this.aggregatedReportDocumentMapper = aggregatedReportDocumentMapper;
    }



    @Override
    public AggregatedReportInfrastructureDTO save(AggregatedReportInfrastructureDTO report) {

        AggregatedReportDocument reportDocument = aggregatedReportDocumentMapper.mapToDocument(report);
        reportDocument.id = aggregatedReportIdGenerator.getNextID();
        if (reportDocument.aggregatedReportName == null)
            reportDocument.addName("Aggregated report#" + reportDocument.id);
        AggregatedReportDocument saved = mongoAggregatedReportsRepository.save(reportDocument);
        return aggregatedReportDocumentMapper.mapFromDocument(saved);

    }

    @Override
    public AggregatedReportInfrastructureDTO findById(String id) {


        return aggregatedReportDocumentMapper.mapFromDocument(mongoAggregatedReportsRepository.findById(id).orElseThrow(() -> new ReportNotFoundException("" + id)));

    }

    @Override
    public List<AggregatedReportInfrastructureDTO> findAll() {
        return mongoAggregatedReportsRepository.findAll().stream().map(aggregatedReportDocumentMapper::mapFromDocument).collect(Collectors.toList());

    }

    @Override
    public List<AggregatedReportInfrastructureDTO> findAll(int pageSize) {

        return mongoAggregatedReportsRepository.findAll(Pageable.ofSize(pageSize)).stream().map(aggregatedReportDocumentMapper::mapFromDocument).collect(Collectors.toList());
        
    }
}
