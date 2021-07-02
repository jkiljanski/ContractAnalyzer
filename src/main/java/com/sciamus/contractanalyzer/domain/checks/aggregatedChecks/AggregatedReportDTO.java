package com.sciamus.contractanalyzer.domain.checks.aggregatedChecks;

import com.sciamus.contractanalyzer.application.FailedCheckDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public class AggregatedReportDTO {
    private String id;
    private String aggregatedReportName;
    private List<String> namesOfChecks;
    private Date timestamp;
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
