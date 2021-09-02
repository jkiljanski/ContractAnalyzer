package com.sciamus.contractanalyzer.application;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AggregatedReportViewDTO {

    public AggregatedReportViewDTO(String id, String aggregatedReportName, List<String> namesOfChecks, LocalDateTime timestamp, List<FailedCheckDTO> failedTests, String passedPercentage, String failedPercentage, String userName) {
        this.id = id;
        this.aggregatedReportName = aggregatedReportName;
        this.namesOfChecks = namesOfChecks;
        this.timestamp = timestamp;
        this.failedTests = failedTests;
        this.passedPercentage = passedPercentage;
        this.failedPercentage = failedPercentage;
        this.userName = userName;
    }

    private String id;
        private String aggregatedReportName;
        private List<String> namesOfChecks;
        private LocalDateTime timestamp;
        private List<FailedCheckDTO> failedTests;
        private String passedPercentage;
        private String failedPercentage;
        private String userName;

        @Override
        public String toString() {
            return "CheckReportDTO{" +
                    "id='" + id + '\'' +
                    ", aggregatedReportName='" + aggregatedReportName + '\'' +
                    ", namesOfChecks='" + namesOfChecks + '\'' +
                    ", timestamp=" + timestamp +
                    ", failedTests='" + failedTests + '\'' +
                    ", passedPercentage='" + passedPercentage + '\'' +
                    ", failedPercentage='" + failedPercentage + '\'' +
                    ", userName='" + userName + '\'' +
                    '}';
        }


}
