package com.sciamus.contractanalyzer.application.mapper.report;


import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.infrastructure.port.ReportInfrastructureDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class ReportInfrastructureMapper {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();


    BoundMapperFacade<ReportInfrastructureDTO, Report> dtoToDocumentMapper= mapperFactory.getMapperFacade(ReportInfrastructureDTO.class, Report.class);


    public Report mapFromDTO (ReportInfrastructureDTO reportInfrastructureDTO) {
        return  dtoToDocumentMapper.map(reportInfrastructureDTO);

    }

    public ReportInfrastructureDTO mapToDTO (Report report) {
        return  dtoToDocumentMapper.mapReverse(report);

    }

}
