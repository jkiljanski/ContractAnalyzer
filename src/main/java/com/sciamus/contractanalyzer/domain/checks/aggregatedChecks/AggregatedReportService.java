package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

import com.sciamus.contractanalyzer.application.FailedCheckDTO;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedChecksRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AggregatedReportService {

    private final AggregatedReportIdGenerator aggregatedReportIdGenerator;

    private final CurrentUserService currentUserService;

    private final ReportService reportService;

    private final RestCheckRepository restCheckRepository;

    private final AggregatedChecksRepository aggregatedChecksRepository;

    @Autowired
    public AggregatedReportService(AggregatedReportIdGenerator aggregatedReportIdGenerator, CurrentUserService currentUserService, ReportService reportService, RestCheckRepository restCheckRepository, AggregatedChecksRepository aggregatedChecksRepository) {
        this.aggregatedReportIdGenerator = aggregatedReportIdGenerator;
        this.currentUserService = currentUserService;
        this.reportService = reportService;
        this.restCheckRepository = restCheckRepository;
        this.aggregatedChecksRepository = aggregatedChecksRepository;
    }

    public AggregatedReport addAggregatedReportToRepository(String name, AggregatedReport aggregatedReport) {
        aggregatedReport.addId(aggregatedReportIdGenerator.getNextID());
        aggregatedReport.addName(name != null ? name : "Aggregated report#" + aggregatedReport.getId());
        return aggregatedChecksRepository.save(aggregatedReport);
    }

    public List<AggregatedReport> getAggregatedChecksReports() {
        return aggregatedChecksRepository.findAll();
    }

    public AggregatedReportDTO runAndSaveAggregatedChecks(String name, List<String> namesOfChecks, String url) throws MalformedURLException {
        double failedTestsNumber = 0;
        ArrayList<Report> failedTestsId = new ArrayList<>();
        ArrayList<FailedCheckDTO> failedTests = new ArrayList<>();
        for (String nameOfCheck: namesOfChecks) {
            Report toSave = restCheckRepository.runCheck(nameOfCheck, new URL(url));
            Report savedReport = reportService.addReportToRepository(toSave);
            if (savedReport.getResult() == ReportResults.FAILED) {
                failedTestsNumber++;
                failedTestsId.add(savedReport);
                failedTests.add(new FailedCheckDTO(savedReport.getId(), savedReport.getNameOfCheck(), savedReport.getReportBody()));
            }
        }
        String passedPercentage = Math.round((1 - failedTestsNumber / namesOfChecks.size())*100) + "%";
        String failedPercentage = Math.round((failedTestsNumber / namesOfChecks.size())*100) + "%";

        AggregatedReport aggregatedReport =
                new AggregatedReport(null, null, namesOfChecks, new Date(), failedTestsId,
                        passedPercentage, failedPercentage, currentUserService.obtainUserName());
        addAggregatedReportToRepository(name, aggregatedReport);
        return mapToDTO(aggregatedReport, failedTests);
    }

    public AggregatedReportDTO mapToDTO(AggregatedReport aggregatedReport, List<FailedCheckDTO> failedCheckDTOS) {
        return new AggregatedReportsBuilder()
                .setAggregatedReportId(aggregatedReport.getId())
                .setAggregatedReportName(aggregatedReport.getAggregatedReportName())
                .setNamesOfChecks(aggregatedReport.getNamesOfChecks())
                .setTimestamp(aggregatedReport.getTimestamp())
                .setFailedTests(failedCheckDTOS)
                .setPassedPercentage(aggregatedReport.getPassedPercentage())
                .setFailedPercentage(aggregatedReport.getFailedPercentage())
                .setUserName(aggregatedReport.getUserName())
                .build();
    }
}
