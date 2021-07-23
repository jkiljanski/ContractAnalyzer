package com.sciamus.contractanalyzer.interfaces.rest;


import com.sciamus.contractanalyzer.application.ReportDTO;
import com.sciamus.contractanalyzer.application.ReportFacade;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class ReportController {

    private final ReportFacade reportFacade;

    public ReportController(ReportFacade reportFacade) {
        this.reportFacade = reportFacade;
    }


    @RolesAllowed("reader")
    @GetMapping("/reports/{id}")
    @ResponseBody
    public ReportDTO GetReportById(
            @PathVariable("id") String id) {
        return reportFacade.getReportByID(id);
    }

    @RolesAllowed("reader")
    @GetMapping("/reports")
    @ResponseBody

    //refactor to DTO:
    public List<ReportDTO> getAllReports() {
        return reportFacade.getAllReports();
    }

    @RolesAllowed("reader")
    @GetMapping("/reports/paged")
    @ResponseBody
    public Page<Report> getPagedReports(@RequestParam("documentsPerPage") int documentsPerPage) {return reportFacade.findByPageSize(documentsPerPage);}



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
