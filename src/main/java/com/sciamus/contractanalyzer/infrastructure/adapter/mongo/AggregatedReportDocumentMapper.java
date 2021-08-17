package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportInfrastructureDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class AggregatedReportDocumentMapper {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();


    BoundMapperFacade<AggregatedReportInfrastructureDTO, AggregatedReportDocument> dtoToDocumentMapper= mapperFactory.getMapperFacade(AggregatedReportInfrastructureDTO.class, AggregatedReportDocument.class);

    public AggregatedReportDocument mapToDocument (AggregatedReportInfrastructureDTO aggregatedReportInfrastructureDTO) {
        return  dtoToDocumentMapper.map(aggregatedReportInfrastructureDTO);

    }

    public AggregatedReportInfrastructureDTO mapFromDocument (AggregatedReportDocument aggregatedReportDocument) {
        return  dtoToDocumentMapper.mapReverse(aggregatedReportDocument);

    }



}
