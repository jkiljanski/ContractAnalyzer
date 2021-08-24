package com.sciamus.contractanalyzer.application;

import io.vavr.control.Option;

public class ReportFilterParameters {
    public final Option<String> result;
    public final Option<String> reportBody;
    public final Option<String> timestampFrom;
    public final Option<String> timestampTo;
    public final Option<String> nameOfCheck;
    public final Option<String> userName;


    public ReportFilterParameters(String result, String reportBody, String timestampFrom, String timestampTo, String nameOfCheck, String userName) {
        this.result = Option.of(result);
        this.reportBody = Option.of(reportBody);
        this.timestampFrom = Option.of(timestampFrom);
        this.timestampTo = Option.of(timestampTo);
        this.nameOfCheck = Option.of(nameOfCheck);
        this.userName = Option.of(userName);
    }



}
