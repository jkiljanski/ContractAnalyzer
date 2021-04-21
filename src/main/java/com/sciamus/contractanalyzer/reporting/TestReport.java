package com.sciamus.contractanalyzer.reporting;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "contractanalyzer")
@TypeAlias("test_report")
public class TestReport {

    //TODO: review: kiedy to pole się tworzy, nie jestem pewien tego rozwiązania

    @Id
    private String id;
    @Field("result")
    private final ReportResults result;
    @Field("content")
    private final String reportBody;
    @Field("name")
    private final String nameOfCheck;
    @Field("timestamp")
            //review: is it precise enough?
    Date timestamp = new Date(System.currentTimeMillis());


    public TestReport(ReportResults result, String reportBody, String nameOfCheck) {

        this.result = result;
        this.reportBody = reportBody;
        this.nameOfCheck = nameOfCheck;
    }

    @PersistenceConstructor
    public TestReport(String id, ReportResults result, String reportBody, Date timestamp, String nameOfCheck) {
        this.id = id;
        this.result = result;
        this.reportBody = reportBody;
        this.timestamp = timestamp;
        this.nameOfCheck = nameOfCheck;
    }

    // nie jestem pewien tego rozwiązania
    void addId(String id) {
        this.id = id;
    }


    public ReportResults isPassed() {
        return result;
    }

    public String getId() {
        return id;
    }

    public ReportResults getResult() {
        return this.result;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public String getReportBody() {
        return reportBody;
    }

    public String getNameOfCheck() {
        return nameOfCheck;
    }




}
