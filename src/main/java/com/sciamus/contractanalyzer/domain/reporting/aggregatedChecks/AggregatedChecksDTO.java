package com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks;

import com.sciamus.contractanalyzer.application.FailedTestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public class AggregatedChecksDTO {
    private String id;
    private String aggregatedReportName;
    private List<String> namesOfChecks;
    private Date timestamp;
    private List<FailedTestDTO> failedTests;
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
