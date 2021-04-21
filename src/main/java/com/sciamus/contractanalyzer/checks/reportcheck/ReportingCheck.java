package com.sciamus.contractanalyzer.checks.reportcheck;

import com.sciamus.contractanalyzer.checks.RestContractCheck;
import com.sciamus.contractanalyzer.reporting.ReportResults;
import com.sciamus.contractanalyzer.reporting.TestReport;
import com.sciamus.contractanalyzer.control.TestReportMapper;
import com.sciamus.contractanalyzer.reporting.TestReportBuilder;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class ReportingCheck implements RestContractCheck {


    private final static String NAME = "Reporting Check";
    private final TestReportMapper testReportMapper;

    TestReportBuilder reportBuilder = new TestReportBuilder();

    public ReportingCheck(TestReportMapper testReportMapper) {
        this.testReportMapper = testReportMapper;
    }


    @Override
    public TestReport run(URL url) {
        ReportingCheckClient reportingCheckClient = Feign.builder()
                .decoder(new GsonDecoder())
                .target(ReportingCheckClient.class, url.toString());

        String checkToRun = URLEncoder.encode(reportingCheckClient
                .getAvailableChecks()
                .listOfChecks.get(0), StandardCharsets.UTF_8)
                .replace("+", "%20");

        TestReport reportSentToDatabase = testReportMapper.
                mapFromDTO(reportingCheckClient.autogen(checkToRun, url));

        TestReport reportFetchedFromDatabase = testReportMapper.
                mapFromDTO(reportingCheckClient.getReportById(reportSentToDatabase.getId()));

        reportBuilder.setNameOfCheck(this.getName())
                .setReportBody("Run on: " +url)
                .createTimestamp();

        if (reportSentToDatabase.getTimestamp().equals(reportFetchedFromDatabase.getTimestamp())) {
            return reportBuilder.setResult(ReportResults.PASSED).createTestReport();
        }
        return  reportBuilder.setResult(ReportResults.FAILED).createTestReport();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
