package com.sciamus.contractanalyzer.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Report not found")
public class ReportNotFoundException extends RuntimeException{

    public ReportNotFoundException(String reportID) {
        super(reportID + " not found");
    }
}
