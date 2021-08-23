package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;


import com.querydsl.core.annotations.QueryEntity;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@QueryEntity
@Document(collection = "checkReports")
@TypeAlias("check_report")
public class ReportDocument {



    @Id
    public String id;
    @Field("result")
    public  ReportResults result;
    @Field("content")
    public String reportBody;
    @Field("name")
    public  String nameOfCheck;
    @Field("timestamp")
    public LocalDateTime timestamp;
    @Field("userName")
    public  String userName;

    public ReportDocument() {
    }

    @PersistenceConstructor
    public ReportDocument(String id, ReportResults result, String reportBody, String nameOfCheck, LocalDateTime timestamp, String userName) {
        this.id = id;
        this.result = result;
        this.reportBody = reportBody;
        this.nameOfCheck = nameOfCheck;
        this.timestamp = timestamp;
        this.userName = userName;
    }





}
