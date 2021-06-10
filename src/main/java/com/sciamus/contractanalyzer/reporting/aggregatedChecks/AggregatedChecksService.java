package com.sciamus.contractanalyzer.reporting.aggregatedChecks;

import com.sciamus.contractanalyzer.checks.RestContractCheckRepository;
import com.sciamus.contractanalyzer.checks.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.reporting.checks.ReportService;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AggregatedChecksService {

    private final RestContractCheckRepository restContractCheckRepository;
    private final AggregatedChecksRepository aggregatedChecksRepository;
    private final AggregatedIdGenerator aggregatedIdGenerator;
    private final CurrentUserService currentUserService;
    private final ReportService reportService;

    public AggregatedChecksService(RestContractCheckRepository restContractCheckRepository, AggregatedChecksRepository aggregatedChecksRepository, AggregatedIdGenerator aggregatedIdGenerator, CurrentUserService currentUserService, ReportService reportService) {
        this.restContractCheckRepository = restContractCheckRepository;
        this.aggregatedChecksRepository = aggregatedChecksRepository;
        this.aggregatedIdGenerator = aggregatedIdGenerator;
        this.currentUserService = currentUserService;
        this.reportService = reportService;
    }

    public AggregatedChecksReport addAggregatedReportToRepository(String name, AggregatedChecksReport aggregatedChecksReport) {
        aggregatedChecksReport.addId(aggregatedIdGenerator.getNextID());
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
            CheckReport toSave = restContractCheckRepository.runCheck(nameOfCheck, new URL(url));
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
