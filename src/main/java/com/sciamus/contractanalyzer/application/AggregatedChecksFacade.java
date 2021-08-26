package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.application.mapper.aggregatedReport.AggregatedReportInfrastructureMapper;
import com.sciamus.contractanalyzer.application.mapper.aggregatedReport.AggregatedReportViewMapper;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReport;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportAccumulator;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportStatistics;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportInfrastructureDTO;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportPersistancePort;
import com.sciamus.contractanalyzer.misc.CurrentUserService;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AggregatedChecksFacade {

    private final RestCheckRepository restCheckRepository;
    private final AggregatedReportPersistancePort aggregatedReportPersistancePort;
    private final AggregatedReportInfrastructureMapper aggregatedReportInfrastructureMapper;
    private final AggregatedReportViewMapper aggregatedReportViewMapper;
    private final CurrentUserService currentUserService;
    private final ChecksFacade checksFacade;


    public AggregatedChecksFacade(RestCheckRepository restCheckRepository, AggregatedReportPersistancePort aggregatedReportPersistancePort, AggregatedReportInfrastructureMapper aggregatedReportInfrastructureMapper, AggregatedReportViewMapper aggregatedReportViewMapper, CurrentUserService currentUserService, ChecksFacade checksFacade) {
        this.restCheckRepository = restCheckRepository;
        this.aggregatedReportPersistancePort = aggregatedReportPersistancePort;
        this.aggregatedReportInfrastructureMapper = aggregatedReportInfrastructureMapper;
        this.aggregatedReportViewMapper = aggregatedReportViewMapper;
        this.currentUserService = currentUserService;
        this.checksFacade = checksFacade;
    }

    public List<AggregatedReportViewDTO> getAggregatedChecksReports() {

        return aggregatedReportPersistancePort.findAll().stream().map(this::convertInfrastractureDTOToViewDTO).collect(Collectors.toList());
    }

    private AggregatedReportViewDTO convertInfrastractureDTOToViewDTO(AggregatedReportInfrastructureDTO dto) {
        return aggregatedReportViewMapper.mapToDTO(aggregatedReportInfrastructureMapper.mapFromDTO(dto));
    }



    public AggregatedReportViewDTO addAggregatedReportToRepository(String name, AggregatedReport aggregatedReport) {
        aggregatedReport.addName(name != null ? name : "Aggregated report#" + aggregatedReport.id);

        return convertInfrastractureDTOToViewDTO(aggregatedReportPersistancePort.save(aggregatedReportInfrastructureMapper.mapToDTO(aggregatedReport)));
    }

    public AggregatedReportViewDTO runAndSaveAggregatedChecks(String name, List<String> namesOfChecks, String url) throws MalformedURLException {
        AggregatedReportStatistics statistics = io.vavr.collection.List.ofAll(namesOfChecks)
                .map(nameOfCheck -> checksFacade.simplifiedRunAndGetSavedAutogeneratedReportWithId(nameOfCheck, url))
                .collect(AggregatedReportStatistics::new,
                        new AggregatedReportAccumulator(),
                        AggregatedReportStatistics::merge);

        AggregatedReport aggregatedChecksReport = getAggregatedChecksReport(namesOfChecks, statistics.getFailedCheckReports().size(), statistics.getFailedCheckReports());

        return addAggregatedReportToRepository(name, aggregatedChecksReport);
    }

    private AggregatedReport getAggregatedChecksReport(java.util.List<String> namesOfChecks, int failedTestsNumber, io.vavr.collection.List<Report> failedTestsReport) {
        String passedPercentage = Math.round((1 - (double) failedTestsNumber / namesOfChecks.size()) * 100) + "%";
        String failedPercentage = Math.round(((double) failedTestsNumber / namesOfChecks.size()) * 100) + "%";

        return new AggregatedReport(null, null, namesOfChecks, LocalDateTime.now(),
                failedTestsReport.toJavaList(), passedPercentage, failedPercentage, currentUserService.obtainUserName());
    }




}
