package com.sciamus.contractanalyzer.application.mapper.report;


import com.sciamus.contractanalyzer.application.ReportViewDTO;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class ReportViewMapper {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    BoundMapperFacade<Report, ReportViewDTO>
            reportToDTOMapper = mapperFactory.getMapperFacade(Report.class, ReportViewDTO.class);



    public Report mapFromDTO(ReportViewDTO reportViewDTO) {
        return reportToDTOMapper.mapReverse(reportViewDTO);
    }

    public ReportViewDTO mapToDTO(Report report) {

        return reportToDTOMapper.map(report);

    }


}
