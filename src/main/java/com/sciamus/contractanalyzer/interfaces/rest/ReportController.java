package com.sciamus.contractanalyzer.interfaces.rest;


import com.sciamus.contractanalyzer.application.ReportFacade;
import com.sciamus.contractanalyzer.application.ReportViewDTO;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportNotFoundException;
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
    public ReportViewDTO GetReportById(
            @PathVariable("id") String id) {
        return reportFacade.getReportByID(id);
    }

    @RolesAllowed("reader")
    @GetMapping("/filteredReports")
    @ResponseBody
    public List<ReportViewDTO> getAllReports(@RequestParam(value = "result",required = false) String result,
                                             @RequestParam(value = "reportBody",required = false) String reportBody,
                                             @RequestParam(value = "timestamp",required = false) String timestamp,
                                             @RequestParam(value = "nameOfCheck",required = false) String nameOfCheck,
                                             @RequestParam(value = "userName",required = false) String userName,
                                             @RequestParam(value = "sortBy", required = false) String sortBy
    ) {

        return sortBy != null ?

                reportFacade.getDescendingSortedFilteredReports(result, reportBody, timestamp, nameOfCheck, userName, sortBy) :

                reportFacade.getFilteredReports(result, reportBody, timestamp, nameOfCheck, userName);
    }


    @RolesAllowed("reader")
    @GetMapping("/reports/paged")
    @ResponseBody
    public List<ReportViewDTO> getPagedReports(@RequestParam("documentsPerPage") int documentsPerPage) {
        return reportFacade.findByPageSize(documentsPerPage);
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
