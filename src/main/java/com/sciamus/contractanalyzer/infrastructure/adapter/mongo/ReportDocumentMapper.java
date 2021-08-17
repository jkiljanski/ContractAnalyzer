package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;


import com.sciamus.contractanalyzer.infrastructure.port.ReportInfrastructureDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class ReportDocumentMapper {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();


    BoundMapperFacade<ReportInfrastructureDTO, ReportDocument> dtoToDocumentMapper= mapperFactory.getMapperFacade(ReportInfrastructureDTO.class, ReportDocument.class);

    public ReportDocument mapToDocument (ReportInfrastructureDTO reportInfrastructureDTO) {
        return  dtoToDocumentMapper.map(reportInfrastructureDTO);

    }

    public ReportInfrastructureDTO mapFromDocument (ReportDocument reportDocument) {
        return  dtoToDocumentMapper.mapReverse(reportDocument);

    }

}
