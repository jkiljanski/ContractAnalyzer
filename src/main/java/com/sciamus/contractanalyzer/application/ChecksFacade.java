package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.application.mapper.ReportMapper;

import java.net.MalformedURLException;
import java.net.URL;

public class ChecksFacade {

    private final RestCheckRepository restCheckRepository;

    private final ReportService reportService;

    private final ReportMapper reportMapper;

    public ChecksFacade(RestCheckRepository restCheckRepository, ReportService reportService, ReportMapper reportMapper) {
        this.restCheckRepository = restCheckRepository;
        this.reportService = reportService;
        this.reportMapper = reportMapper;
    }

    public ReportDTO runAndGetSavedReportWithId(String name, String url) throws MalformedURLException {

        Report toSave = restCheckRepository.runCheck(name, new URL(url));
        reportService.addReportToRepository(toSave);
        return reportMapper.mapToDTO(toSave);

    }

    public ReportDTO runAndGetSavedAutogeneratedReportWithId(String name, String url) throws MalformedURLException {

        Report toSave = restCheckRepository.runCheck(name, new URL(url)).addAutogenerated();
        reportService.addReportToRepository(toSave);

        return reportMapper.mapToDTO(toSave);

    }

}