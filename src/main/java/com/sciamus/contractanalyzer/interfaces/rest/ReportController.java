package com.sciamus.contractanalyzer.interfaces.rest;


import com.sciamus.contractanalyzer.domain.checks.reports.ReportNotFoundException;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class ReportController {

    //TODO: fix me: this cannot use domain object directly -> it always must got through application layer
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RolesAllowed("reader")
    @GetMapping("/reports/{id}")
    @ResponseBody
    public Report GetReportById(
            @PathVariable("id") String id) {
        return reportService.getReportByID(id);
    }

    @RolesAllowed("reader")
    @GetMapping("/reports")
    @ResponseBody

    //refactor to DTO:
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }


    @ExceptionHandler(ReportNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementFoundException(
            ReportNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }




}
