package com.sciamus.contractanalyzer.domain.checks.getlistof;

import com.sciamus.contractanalyzer.domain.checks.RestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.reportcheck.ReportingCheckClient;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import feign.Feign;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class GetListOfContractChecksCheck implements RestContractCheck {

    private final static String NAME = "Get List Of Checks Check";
    URL urlSubjectToTest;

    private final RequestInterceptor requestInterceptor;


    public GetListOfContractChecksCheck(RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }


    @Override
    public CheckReport run(URL url, CheckReportBuilder builder) {

        urlSubjectToTest = url;

        ReportingCheckClient testClient = feignClient(url);

        ListOfChecksDTO responseDTO;

        responseDTO = testClient.getAvailableChecks();

        builder.setReportBody("Run on "+ urlSubjectToTest).createTimestamp().setNameOfCheck(this.getName());

        if (responseDTO.listOfChecks.size() > 0 && responseDTO.listOfChecks.contains(GetListOfContractChecksCheck.NAME)) {

            return getPassedTestReport(builder);
        }
        return getFailedTestReport(builder);
    }

    private ReportingCheckClient feignClient(URL url) {
        return Feign.builder()
                .decoder(new GsonDecoder())
                .requestInterceptor(requestInterceptor)
                .target(ReportingCheckClient.class, url.toString());
    }

// there are no cases in which this test fails.

    private CheckReport getFailedTestReport(CheckReportBuilder builder) {
        return builder
                .addTextToBody("We couldn't get list of checks")
                .setResult(ReportResults.FAILED)
                .build();
    }

    private CheckReport getPassedTestReport(CheckReportBuilder builder) {
        return builder
                .setResult(ReportResults.PASSED)
                .build();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
