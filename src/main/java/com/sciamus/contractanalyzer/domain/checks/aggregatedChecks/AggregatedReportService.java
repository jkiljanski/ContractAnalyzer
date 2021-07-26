package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;


import com.sciamus.contractanalyzer.application.FailedCheckDTO;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportsRepository;
import com.sciamus.contractanalyzer.misc.CurrentUserService;
import io.vavr.Function2;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Date;
import java.util.stream.Collectors;


public class AggregatedReportService {

    private final AggregatedReportIdGenerator aggregatedReportIdGenerator;

    private final CurrentUserService currentUserService;

    private final ReportService reportService;

    private RestCheckRepository restCheckRepository;

    private final AggregatedReportsRepository aggregatedReportsRepository;

    @Autowired
    public AggregatedReportService(AggregatedReportIdGenerator aggregatedReportIdGenerator, CurrentUserService currentUserService, ReportService reportService, RestCheckRepository restCheckRepository, AggregatedReportsRepository aggregatedReportsRepository) {
        this.aggregatedReportIdGenerator = aggregatedReportIdGenerator;
        this.currentUserService = currentUserService;
        this.reportService = reportService;
        this.restCheckRepository = restCheckRepository;
        this.aggregatedReportsRepository = aggregatedReportsRepository;
    }

    public AggregatedReport addAggregatedReportToRepository(String name, AggregatedReport aggregatedChecksReport) {
        aggregatedChecksReport.addId(aggregatedReportIdGenerator.getNextID());
        aggregatedChecksReport.addName(name != null ? name : "Aggregated report#" + aggregatedChecksReport.getId());
        return aggregatedReportsRepository.save(aggregatedChecksReport);
    }

    public java.util.List<AggregatedReport> getAggregatedChecksReports() {
        return aggregatedReportsRepository.findAll();
    }

    public Report saveReportToRepository(Report Report) throws RuntimeException {
        return reportService.addReportToRepository(Report);
    }

    public Function2<String, String, Report> runAndSaveCheckToRepository = (name, stringUrl) ->
            saveReportToRepository(restCheckRepository.runCheck(name, generateURL(stringUrl)));

    private URL generateURL(String stringUrl) throws IllegalArgumentException {
        return Try.of(() -> new URL(stringUrl)).
                getOrElseThrow(() -> new IllegalArgumentException("Bad URL!"));
    }

    public AggregatedReportDTO runAndSaveAggregatedChecks(String name, java.util.List<String> namesOfChecks, String url) throws RuntimeException {

        AggregatedReportStatistics statistics = List.ofAll(namesOfChecks)
                .map(nameOfCheck -> runAndSaveCheckToRepository.apply(nameOfCheck, url))
                .collect(AggregatedReportStatistics::new,
                        new AggregatedReportAccumulator(),
                        AggregatedReportStatistics::merge);

        AggregatedReport aggregatedChecksReport = getAggregatedChecksReport(namesOfChecks, statistics.getFailedCheckReports().size(), statistics.getFailedCheckReports());
        addAggregatedReportToRepository(name, aggregatedChecksReport);
        return mapToDTO(aggregatedChecksReport, statistics.getFailedCheckReports());
    }

    private AggregatedReport getAggregatedChecksReport(java.util.List<String> namesOfChecks, int failedTestsNumber, List<Report> failedTestsReport) {
        String passedPercentage = Math.round((1 - (double) failedTestsNumber / namesOfChecks.size()) * 100) + "%";
        String failedPercentage = Math.round(((double) failedTestsNumber / namesOfChecks.size()) * 100) + "%";

        return new AggregatedReport(null, null, namesOfChecks, new Date(),
                failedTestsReport.toJavaList(), passedPercentage, failedPercentage, currentUserService.obtainUserName());
    }

    public List<FailedCheckDTO> mapFailedTests(List<Report> failedTestReports) {
        return List.ofAll(failedTestReports.map(failedTestReport ->
                new FailedCheckDTO(failedTestReport.getId(), failedTestReport.getNameOfCheck(), failedTestReport.getReportBody())
        ).collect(Collectors.toList()));
    }

    public AggregatedReportDTO mapToDTO(AggregatedReport aggregatedChecksReport, List<Report> failedCheckReports) {
        return new AggregatedReportsBuilder()
                .setAggregatedReportId(aggregatedChecksReport.getId())
                .setAggregatedReportName(aggregatedChecksReport.getAggregatedReportName())
                .setNamesOfChecks(List.ofAll(aggregatedChecksReport.getNamesOfChecks()))
                .setTimestamp(aggregatedChecksReport.getTimestamp())
                .setFailedChecks(mapFailedTests(failedCheckReports))
                .setPassedPercentage(aggregatedChecksReport.getPassedPercentage())
                .setFailedPercentage(aggregatedChecksReport.getFailedPercentage())
                .setUserName(aggregatedChecksReport.getUserName())
                .build();
    }
}