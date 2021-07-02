package com.sciamus.contractanalyzer.domain.checks.rest.reportcheck;

import com.sciamus.contractanalyzer.domain.checks.rest.RestCheck;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.application.mapper.ReportMapper;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;
import feign.Feign;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ReportingCheck implements RestCheck {

    private final static String NAME = "Reporting Check";

    private final ReportMapper reportMapper;

    private final RequestInterceptor interceptor;

    public ReportingCheck(ReportMapper reportMapper, RequestInterceptor interceptor) {
        this.reportMapper = reportMapper;
        this.interceptor = interceptor;
    }

    @Override
    public Report run(URL url, ReportBuilder reportBuilder) {
        ReportingCheckClient reportingCheckClient = Feign.builder()
                .decoder(new GsonDecoder())
                .requestInterceptor(interceptor)
                .target(ReportingCheckClient.class, url.toString());

        String checkToRun = URLEncoder.encode(reportingCheckClient
                .getAvailableChecks()
                .listOfChecks.get(0), StandardCharsets.UTF_8)
                .replace("+", "%20");

        Report reportSentToDatabase = reportMapper.
                mapFromDTO(reportingCheckClient.autogenerate(checkToRun, url));

        Report reportFetchedFromDatabase = reportMapper.
                mapFromDTO(reportingCheckClient.getReportById(reportSentToDatabase.getId()));

        reportBuilder.setNameOfCheck(this.getName())
                .setReportBody("Run on: " +url)
                .createTimestamp();

        if (reportSentToDatabase.getTimestamp().equals(reportFetchedFromDatabase.getTimestamp())) {
            return reportBuilder
                    .setResult(ReportResults.PASSED)
                    .build();
        }
        return  reportBuilder
                .setResult(ReportResults.FAILED)
                .build();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
