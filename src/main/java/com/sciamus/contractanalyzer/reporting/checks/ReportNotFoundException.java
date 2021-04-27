package com.sciamus.contractanalyzer.reporting.checks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Report not found")
public class ReportNotFoundException extends IllegalArgumentException{

    public ReportNotFoundException(String reportID) {
        super("Sorry, we're unable to find report with ID: "+ reportID);
    }
}
