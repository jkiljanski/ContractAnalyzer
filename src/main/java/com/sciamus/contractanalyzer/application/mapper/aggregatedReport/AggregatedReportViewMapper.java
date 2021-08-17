package com.sciamus.contractanalyzer.application.mapper.aggregatedReport;

import com.sciamus.contractanalyzer.application.AggregatedReportViewDTO;
import com.sciamus.contractanalyzer.application.FailedCheckDTO;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReport;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class AggregatedReportViewMapper {


    private  BoundMapperFacade<AggregatedReportViewDTO, AggregatedReport> init() {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(AggregatedReport.class, AggregatedReportViewDTO.class)
                .byDefault()
                .field("failedTestReportList", "failedTests")
                .register();

        mapperFactory.classMap(Report.class, FailedCheckDTO.class)
                .field("id","id")
                .field("nameOfCheck","nameOfCheck")
                .field("reportBody","reportBody")
                .register();

        return   mapperFactory.getMapperFacade(AggregatedReportViewDTO.class, AggregatedReport.class);
    }

    public AggregatedReportViewDTO mapToDTO(AggregatedReport aggregatedReport) {

        return  init().mapReverse(aggregatedReport);

    }

    AggregatedReport mapFromDTO (AggregatedReportViewDTO aggregatedReportViewDTO) {
        return  init().map(aggregatedReportViewDTO);

    }






}

