package com.sciamus.contractanalyzer.application;

import lombok.Getter;

@Getter
public class FailedCheckDTO {
    private String id;
    private String nameOfCheck;
    private String reportBody;

    public FailedCheckDTO(String id, String nameOfCheck, String reportBody) {
        this.id = id;
        this.nameOfCheck = nameOfCheck;
        this.reportBody = reportBody;
    }
}
