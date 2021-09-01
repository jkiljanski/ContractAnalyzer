package com.sciamus.contractanalyzer.application.mapper.queries;

import com.sciamus.contractanalyzer.application.ReportFilterParameters;
import com.sciamus.contractanalyzer.infrastructure.port.ReportFilterParametersInfrastructureDTO;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class ReportFilterParametersMapper {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();


    BoundMapperFacade<ReportFilterParametersInfrastructureDTO, ReportFilterParameters> dtoToDocumentMapper = mapperFactory.getMapperFacade(ReportFilterParametersInfrastructureDTO.class, ReportFilterParameters.class);


    public ReportFilterParameters mapFromDTO(ReportFilterParametersInfrastructureDTO reportInfrastructureDTO) {
        return dtoToDocumentMapper.map(reportInfrastructureDTO);

    }

    public ReportFilterParametersInfrastructureDTO mapToDTO(ReportFilterParameters reportFilterParameters) {
        return dtoToDocumentMapper.mapReverse(reportFilterParameters);

    }

}
