package com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks;

import com.sciamus.contractanalyzer.application.FailedTestDTO;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import com.sciamus.contractanalyzer.domain.reporting.idGenerator.AggregatedReportIdGenerator;
import io.vavr.Function2;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static io.vavr.API.*;

public class AggregatedChecksService {

    private final AggregatedReportIdGenerator aggregatedReportIdGenerator;

    private final CurrentUserService currentUserService;

    //TODO final
    private ReportService reportService;

    //TODO final
    private CheckRepository checkRepository;

    private final AggregatedChecksRepository aggregatedChecksRepository;

    @Autowired
    public AggregatedChecksService(AggregatedReportIdGenerator aggregatedReportIdGenerator, CurrentUserService currentUserService, ReportService reportService, CheckRepository checkRepository, AggregatedChecksRepository aggregatedChecksRepository) {
        this.aggregatedReportIdGenerator = aggregatedReportIdGenerator;
        this.currentUserService = currentUserService;
        this.reportService = reportService;
        this.checkRepository = checkRepository;
        this.aggregatedChecksRepository = aggregatedChecksRepository;
    }

    public AggregatedChecksReport addAggregatedReportToRepository(String name, AggregatedChecksReport aggregatedChecksReport) {
        aggregatedChecksReport.addId(aggregatedReportIdGenerator.getNextID());
        aggregatedChecksReport.addName(name != null ? name : "Aggregated report#" + aggregatedChecksReport.getId());
        return aggregatedChecksRepository.save(aggregatedChecksReport);
    }

    public java.util.List<AggregatedChecksReport> getAggregatedChecksReports() {
        return aggregatedChecksRepository.findAll();
    }

    public CheckReport saveReportToRepository(CheckReport checkReport) throws RuntimeException {
        return reportService.addReportToRepository(checkReport);
    }

    public Function2<String, String, CheckReport> runAndSaveCheckToRepository = (name, stringUrl) ->
                    saveReportToRepository(checkRepository.runCheck(name, generateURL(stringUrl)));

    private URL generateURL(String stringUrl) throws IllegalArgumentException {
        return Try.of(() -> new URL(stringUrl)).
                getOrElseThrow(() -> new IllegalArgumentException("Bad URL!"));
    }

    public AggregatedChecksDTO runAndSaveAggregatedChecks(String name, java.util.List<String> namesOfChecks, String url)  throws RuntimeException {

        AggregatedChecksStatistics statistics = List.ofAll(namesOfChecks)
                .map(nameOfCheck -> runAndSaveCheckToRepository.apply(nameOfCheck, url))
                .collect(AggregatedChecksStatistics::new,
                        new AggregatedChecksAccumulator(),
                        AggregatedChecksStatistics::merge);

        AggregatedChecksReport aggregatedChecksReport = getAggregatedChecksReport(namesOfChecks, statistics.getFailedTestReports().size(), statistics.getFailedTestReports());
        addAggregatedReportToRepository(name, aggregatedChecksReport);
        return mapToDTO(aggregatedChecksReport, statistics.getFailedTestReports());
    }

    private AggregatedChecksReport getAggregatedChecksReport(java.util.List<String> namesOfChecks, int failedTestsNumber, List<CheckReport> failedTestsReport) {
        String passedPercentage = Math.round((1 - (double) failedTestsNumber / namesOfChecks.size())*100) + "%";
        String failedPercentage = Math.round(( (double) failedTestsNumber / namesOfChecks.size())*100) + "%";

        AggregatedChecksReport aggregatedChecksReport =
                new AggregatedChecksReport(null, null, namesOfChecks, new Date(), failedTestsReport.toJavaList(),
                        passedPercentage, failedPercentage, currentUserService.obtainUserName());
        return aggregatedChecksReport;
    }

    public List<FailedTestDTO> mapFailedTests(List<CheckReport> failedTestReports) {
        return List.ofAll(failedTestReports.map(failedTestReport ->
            new FailedTestDTO(failedTestReport.getId(), failedTestReport.getNameOfCheck(), failedTestReport.getReportBody())
        ).collect(Collectors.toList()));
    }

    public AggregatedChecksDTO mapToDTO(AggregatedChecksReport aggregatedChecksReport, List<CheckReport> failedTestReports) {
        return new AggregatedChecksBuilder()
                .setAggregatedReportId(aggregatedChecksReport.getId())
                .setAggregatedReportName(aggregatedChecksReport.getAggregatedReportName())
                .setNamesOfChecks(List.ofAll(aggregatedChecksReport.getNamesOfChecks()))
                .setTimestamp(aggregatedChecksReport.getTimestamp())
                .setFailedTests(mapFailedTests(failedTestReports))
                .setPassedPercentage(aggregatedChecksReport.getPassedPercentage())
                .setFailedPercentage(aggregatedChecksReport.getFailedPercentage())
                .setUserName(aggregatedChecksReport.getUserName())
                .build();
    }
}
