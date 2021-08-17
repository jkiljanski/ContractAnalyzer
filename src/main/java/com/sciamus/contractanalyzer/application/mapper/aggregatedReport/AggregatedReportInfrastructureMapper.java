package com.sciamus.contractanalyzer.application.mapper.aggregatedReport;

import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReport;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportInfrastructureDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class AggregatedReportInfrastructureMapper {


    private BoundMapperFacade<AggregatedReportInfrastructureDTO, AggregatedReport> init() {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(AggregatedReport.class, AggregatedReportInfrastructureDTO.class)
                .byDefault().register();

        return mapperFactory.getMapperFacade(AggregatedReportInfrastructureDTO.class, AggregatedReport.class);
    }

    public AggregatedReportInfrastructureDTO mapToDTO(AggregatedReport aggregatedReport) {

        return init().mapReverse(aggregatedReport);

    }

    public AggregatedReport mapFromDTO(AggregatedReportInfrastructureDTO aggregatedReportViewDTO) {
        return init().map(aggregatedReportViewDTO);

    }

}
