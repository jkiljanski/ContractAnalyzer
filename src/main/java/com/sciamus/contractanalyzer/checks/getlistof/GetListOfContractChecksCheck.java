package com.sciamus.contractanalyzer.checks.getlistof;

import com.sciamus.contractanalyzer.checks.RestContractCheck;
import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.CheckReportBuilder;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class GetListOfContractChecksCheck implements RestContractCheck {


    private final static String NAME = "Get List Of Checks Check";

    URL urlSubjectToTest;

    CheckReportBuilder builder = new CheckReportBuilder();

    @Override
    public CheckReport run(URL url) {

        urlSubjectToTest = url;


        GetListOfContractChecksCheckClient testClient = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GetListOfContractChecksCheckClient.class, url.toString());


        GetListOfContractChecksCheckResponseDTO responseDTO;

        responseDTO = testClient.getListOfChecks();

        builder.setReportBody("Run on "+ urlSubjectToTest).createTimestamp().setNameOfCheck(this.getName());



        if (responseDTO.listOfChecks.size() > 0 && responseDTO.listOfChecks.contains(GetListOfContractChecksCheck.NAME)) {

            return getPassedTestReport(builder);
        }
        return getFailedTestReport(builder);
    }

// there are no cases in which this test fails.

    private CheckReport getFailedTestReport(CheckReportBuilder builder) {
        return builder
                .addTextToBody("We couldn't get list of checks")
                .setResult(ReportResults.FAILED)
                .createTestReport();
    }

    private CheckReport getPassedTestReport(CheckReportBuilder builder) {
        return builder
                .setResult(ReportResults.PASSED)
                .createTestReport();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
