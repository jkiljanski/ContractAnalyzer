package com.sciamus.contractanalyzer.checks.getlistof;

import com.sciamus.contractanalyzer.checks.RestContractCheck;
import com.sciamus.contractanalyzer.reporting.ReportResults;
import com.sciamus.contractanalyzer.reporting.TestReport;
import com.sciamus.contractanalyzer.reporting.TestReportBuilder;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class GetListOfContractChecksCheck implements RestContractCheck {


    private final static String NAME = "List Contract Check Getting Check";

    URL urlSubjectToTest;

    TestReportBuilder builder =new TestReportBuilder();

    @Override
    public TestReport run(URL url) {

        urlSubjectToTest = url;


        GetListOfContractChecksCheckClient testClient = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GetListOfContractChecksCheckClient.class, url.toString());

//        System.out.println(testClient.getListOfChecks());

        GetListOfContractChecksCheckResponseDTO responseDTO;

        responseDTO = testClient.getListOfChecks();

        builder.setReportBody("Run on "+ urlSubjectToTest).createTimestamp().setNameOfCheck(this.getName());



        if (responseDTO.listOfChecks.size() > 0 && responseDTO.listOfChecks.contains(GetListOfContractChecksCheck.NAME)) {


            return getPassedTestReport(builder);
        }
        return getFailedTestReport(builder);
    }


    private TestReport getFailedTestReport(TestReportBuilder builder) {
        return builder
                .setResult(ReportResults.FAILED)
                .createTestReport();
    }

    private TestReport getPassedTestReport(TestReportBuilder builder) {
        return builder
                .setResult(ReportResults.PASSED)
                .createTestReport();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
