package com.sciamus.contractanalyzer.reporting.aggregatedChecks;

import lombok.Getter;

@Getter
public class FailedTestDTO {
    private String id;
    private String nameOfCheck;
    private String reportBody;

    public FailedTestDTO(String id, String nameOfCheck, String reportBody) {
        this.id = id;
        this.nameOfCheck = nameOfCheck;
        this.reportBody = reportBody;
    }
}
