package com.sciamus.contractanalyzer.domain.checks.rest.getlistof;

import com.sciamus.contractanalyzer.domain.checks.rest.RestCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.ReportingCheckClient;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;
import feign.Feign;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;

import java.net.URL;

public class GetListOfChecksCheck implements RestCheck {

    private final static String NAME = "Get List Of Checks Check";
    URL urlSubjectToTest;

    private final RequestInterceptor requestInterceptor;

    public GetListOfChecksCheck(RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public Report run(URL url, ReportBuilder builder) {

        urlSubjectToTest = url;

        ReportingCheckClient testClient = feignClient(url);

        ListOfChecksDTO responseDTO;

        responseDTO = testClient.getAvailableChecks();

        builder.setReportBody("Run on "+ urlSubjectToTest).createTimestamp().setNameOfCheck(this.getName());

        if (responseDTO.listOfChecks.size() > 0 && responseDTO.listOfChecks.contains(GetListOfChecksCheck.NAME)) {

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

    private Report getFailedTestReport(ReportBuilder builder) {
        return builder
                .addTextToBody("We couldn't get list of checks")
                .setResult(ReportResults.FAILED)
                .build();
    }

    private Report getPassedTestReport(ReportBuilder builder) {
        return builder
                .setResult(ReportResults.PASSED)
                .build();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
