package com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks;

import com.sciamus.contractanalyzer.application.ContractChecksService;
import com.sciamus.contractanalyzer.application.FailedTestDTO;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import com.sciamus.contractanalyzer.domain.reporting.idGenerator.AggregatedReportIdGenerator;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AggregatedChecksService {

    private final ContractChecksService contractChecksService;
    private final AggregatedChecksRepository aggregatedChecksRepository;
    private final AggregatedReportIdGenerator aggregatedReportIdGenerator;
    private final CurrentUserService currentUserService;
    private final ReportService reportService;

    public AggregatedChecksService(ContractChecksService contractChecksService, AggregatedChecksRepository aggregatedChecksRepository, AggregatedReportIdGenerator aggregatedReportIdGenerator, CurrentUserService currentUserService, ReportService reportService) {
        this.contractChecksService = contractChecksService;
        this.aggregatedChecksRepository = aggregatedChecksRepository;
        this.aggregatedReportIdGenerator = aggregatedReportIdGenerator;
        this.currentUserService = currentUserService;
        this.reportService = reportService;
    }

    public AggregatedChecksReport addAggregatedReportToRepository(String name, AggregatedChecksReport aggregatedChecksReport) {
        aggregatedChecksReport.addId(aggregatedReportIdGenerator.getNextID());
        aggregatedChecksReport.addName(name != null ? name : "Aggregated report#" + aggregatedChecksReport.getId());
        return aggregatedChecksRepository.save(aggregatedChecksReport);
    }

    public List<AggregatedChecksReport> getAggregatedChecksReports() {
        return aggregatedChecksRepository.findAll();
    }

    public AggregatedChecksDTO runAndSaveAggregatedChecks(String name, List<String> namesOfChecks, String url) throws MalformedURLException {
        double failedTestsNumber = 0;
        ArrayList<CheckReport> failedTestsId = new ArrayList<>();
        ArrayList<FailedTestDTO> failedTests = new ArrayList<>();
        for (String nameOfCheck: namesOfChecks) {
            CheckReport toSave = contractChecksService.runCheck(nameOfCheck, new URL(url));
            CheckReport savedReport = reportService.addReportToRepository(toSave);

            if (savedReport.getResult() == ReportResults.FAILED) {
                failedTestsNumber++;
                failedTestsId.add(savedReport);
                failedTests.add(new FailedTestDTO(savedReport.getId(), savedReport.getNameOfCheck(), savedReport.getReportBody()));
            }
        }
        String passedPercentage = Math.round((1 - failedTestsNumber / namesOfChecks.size())*100) + "%";
        String failedPercentage = Math.round((failedTestsNumber / namesOfChecks.size())*100) + "%";

        AggregatedChecksReport aggregatedChecksReport =
                new AggregatedChecksReport(null, null, namesOfChecks, new Date(), failedTestsId,
                        passedPercentage, failedPercentage, currentUserService.obtainUserName());
        addAggregatedReportToRepository(name, aggregatedChecksReport);
        return mapToDTO(aggregatedChecksReport, failedTests);
    }

    public AggregatedChecksDTO mapToDTO(AggregatedChecksReport aggregatedChecksReport, List<FailedTestDTO> failedTestDTOS) {
        return new AggregatedChecksBuilder()
                .setAggregatedReportId(aggregatedChecksReport.getId())
                .setAggregatedReportName(aggregatedChecksReport.getAggregatedReportName())
                .setNamesOfChecks(aggregatedChecksReport.getNamesOfChecks())
                .setTimestamp(aggregatedChecksReport.getTimestamp())
                .setFailedTests(failedTestDTOS)
                .setPassedPercentage(aggregatedChecksReport.getPassedPercentage())
                .setFailedPercentage(aggregatedChecksReport.getPassedPercentage())
                .setUserName(aggregatedChecksReport.getUserName())
                .build();
    }
}
