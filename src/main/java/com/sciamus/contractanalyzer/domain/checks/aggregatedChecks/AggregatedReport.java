package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

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
public class AggregatedReport {

        @Id
        private String id;

        @Field("aggregatedReportName")
        private String aggregatedReportName;

        @Field("namesOfChecks")
        private final List<String> namesOfChecks;

        @Field("timestamp")
        private Date timestamp;

//        @DBRef
//        @Field("failedTestsId")
//        private final List<Check> failedTestsId;

        @DBRef
        @Field("failedTestsId")
        private final List<Report> failedTestsId;

        @Field("passedPercentage")
        private final String passedPercentage;

        @Field("failedPercentage")
        private final String failedPercentage;

        @Field("userName")
        private final String userName;


        @PersistenceConstructor
        public AggregatedReport(String id, String aggregatedReportName, List<String> namesOfChecks, Date timestamp, List<Report> failedTestsId, String passedPercentage, String failedPercentage, String userName) {
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
