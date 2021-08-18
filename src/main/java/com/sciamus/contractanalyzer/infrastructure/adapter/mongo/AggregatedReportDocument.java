package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;


@Document(collection = "aggregatedCheckReports")
@TypeAlias("aggregated_check_report")
@Getter
public class AggregatedReportDocument {

    @Id
    public String id;

    @Field("aggregatedReportName")
    public String aggregatedReportName;

    @Field("namesOfChecks")
     public List<String> namesOfChecks;

    @Field("timestamp")
    public Date timestamp;

    public AggregatedReportDocument() {
    }


    //        @DBRef
//        @Field("failedTestsId")
//         public List<Check> failedTestsId;

    @DBRef
    @Field("failedTestsId")
     public List<Report> failedTestsId;

    @Field("passedPercentage")
     public String passedPercentage;

    @Field("failedPercentage")
     public String failedPercentage;

    @Field("userName")
     public String userName;


    @PersistenceConstructor
    public AggregatedReportDocument(String id, String aggregatedReportName, List<String> namesOfChecks, Date timestamp, List<Report> failedTestsId, String passedPercentage, String failedPercentage, String userName) {
        this.id = id;
        this.aggregatedReportName = aggregatedReportName;
        this.namesOfChecks = namesOfChecks;
        this.timestamp = timestamp;
        this.failedTestsId = failedTestsId;
        this.passedPercentage = passedPercentage;
        this.failedPercentage = failedPercentage;
        this.userName = userName;
    }

    void addId(String id) {
        this.id = id;
    }

    void addName(String name) {
        this.aggregatedReportName = name;
    }

    @Override
    public String toString() {
        return "AggregatedChecksReport{" +
                "id='" + id + '\'' +
                ", aggregatedReportName=" + aggregatedReportName +
                ", namesOfChecks='" + namesOfChecks + '\'' +
                ", timestamp=" + timestamp + '\'' +
                ", failedTestsId='" + failedTestsId + '\'' +
                ", passedPercentage='" + passedPercentage + '\'' +
                ", failedPercentage='" + failedPercentage + '\'' +
                ", userName='" + userName +
                '}';
    }



}
