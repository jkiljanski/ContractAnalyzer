package com.sciamus.contractanalyzer.checks.reportcheck;

import com.sciamus.contractanalyzer.checks.RestContractCheck;
import com.sciamus.contractanalyzer.reporting.ReportResults;
import com.sciamus.contractanalyzer.reporting.TestReport;
import com.sciamus.contractanalyzer.control.TestReportMapper;
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

        System.out.println(checkToRun);


        TestReport reportToBeChecked1 = testReportMapper.
                mapFromDTO(reportingCheckClient.runCheckAndGetReportWithId(checkToRun, url));

        TestReport reportToBeChecked2 = testReportMapper.
                mapFromDTO(reportingCheckClient.getReportById(reportToBeChecked1.getReportId()));

//        System.out.println("the compared timestamps:");
//        System.out.println(reportToBeChecked1.getTimestamp());
//        System.out.println(reportToBeChecked2.getTimestamp());


        if (reportToBeChecked1.getTimestamp().equals(reportToBeChecked2.getTimestamp())) {
            return new TestReport(ReportResults.PASSED, this.getName() + " PASSED");
        }
        return new TestReport(ReportResults.FAILED, this.getName() + " FAILED");

    }

    @Override
    public String getName() {
        return NAME;
    }
}
