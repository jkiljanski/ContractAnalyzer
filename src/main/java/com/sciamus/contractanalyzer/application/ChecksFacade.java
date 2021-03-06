package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.application.mapper.report.ReportInfrastructureMapper;
import com.sciamus.contractanalyzer.application.mapper.report.ReportViewMapper;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.infrastructure.port.ReportInfrastructureDTO;
import com.sciamus.contractanalyzer.infrastructure.port.ReportPersistancePort;
import io.vavr.control.Try;

import java.net.URL;
import java.util.List;

public class ChecksFacade {

    private final RestCheckRepository restCheckRepository;

    private final ReportFacade reportFacade;

    private final ReportPersistancePort reportPersistancePort;


    private final ReportViewMapper reportViewMapper;

    private final ReportInfrastructureMapper reportInfrastructureMapper;

    public ChecksFacade(RestCheckRepository restCheckRepository, ReportFacade reportFacade, ReportPersistancePort reportPersistancePort, ReportViewMapper reportViewMapper, ReportInfrastructureMapper reportInfrastructureMapper) {
        this.restCheckRepository = restCheckRepository;
        this.reportFacade = reportFacade;
        this.reportPersistancePort = reportPersistancePort;
        this.reportViewMapper = reportViewMapper;
        this.reportInfrastructureMapper = reportInfrastructureMapper;
    }

    public ReportViewDTO runAndGetSavedReportWithId(String name, String url) {

        Report toSave = restCheckRepository.runCheck(name, getUrl(url));
        ReportInfrastructureDTO savedReportDTO = reportPersistancePort.save(reportInfrastructureMapper.mapToDTO(toSave));

        return reportViewMapper.mapToDTO(reportInfrastructureMapper.mapFromDTO(savedReportDTO));

    }

    public ReportViewDTO runAndGetSavedAutogeneratedReportWithId(String name, String url) {

        Report report = simplifiedRunAndGetSavedAutogeneratedReportWithId(name, url);
        return reportViewMapper.mapToDTO(report);

    }

    Report simplifiedRunAndGetSavedAutogeneratedReportWithId(String name, String url) {
        Report toSave = restCheckRepository.runCheck(name, getUrl(url)).addAutogenerated();
        ReportInfrastructureDTO savedReportDTO = reportPersistancePort.save(reportInfrastructureMapper.mapToDTO(toSave));
        Report report = reportInfrastructureMapper.mapFromDTO(savedReportDTO);
        return report;
    }

    private URL getUrl(String url) {
        return Try.of(() -> new URL(url))
                .getOrElseThrow(ex -> new RuntimeException("Invalid URL: " + url, ex));

    }

    public List<String> returnAllChecksList() {
     return    restCheckRepository.getAllChecks();
    }




}
