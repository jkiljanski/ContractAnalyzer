package com.sciamus.contractanalyzer.domain.checks.rest.reportcheck;

import com.sciamus.contractanalyzer.application.ReportDTO;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheck;
import feign.Feign;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;
import io.vavr.control.Option;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ReportingCheck implements RestCheck {

    private final static String NAME = "Reporting Check";

    private final RequestInterceptor interceptor;

    public ReportingCheck(RequestInterceptor interceptor) {
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

        ReportDTO reportSentToDatabase = reportingCheckClient.autogenerate(checkToRun, url);

        ReportDTO reportFetchedFromDatabase = reportingCheckClient.getReportById(reportSentToDatabase.id);



        reportBuilder.setNameOfCheck(this.getName())
                .setReportBody("Run on: " +url)
                .createTimestamp();

        return Option.of(reportSentToDatabase.getTimestamp())
                .filter(r -> r.equals(reportFetchedFromDatabase.getTimestamp()))
                .map(r -> reportBuilder
                        .setResult(ReportResults.PASSED)
                        .build())
                .getOrElse(() -> reportBuilder
                        .setResult(ReportResults.FAILED)
                        .build());
    }

    @Override
    public String getName() {
        return NAME;
    }

}
