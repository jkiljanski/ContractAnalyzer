package com.sciamus.contractanalyzer.interfaces.rest;


import com.sciamus.contractanalyzer.application.ReportFacade;
import com.sciamus.contractanalyzer.application.ReportViewDTO;
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
    public ReportViewDTO GetReportById(
            @PathVariable("id") String id) {
        return reportFacade.getReportByID(id);
    }

    @RolesAllowed("reader")
    @GetMapping("/filteredReports")
    @ResponseBody
    public List<ReportViewDTO> getAllReports(@RequestParam("result") String result,
                                             @RequestParam("reportBody") String reportBody,
                                             @RequestParam("timestamp") String timestamp,
                                             @RequestParam("nameOfCheck") String nameOfCheck,
                                             @RequestParam("userName") String userName) {
        return reportFacade.getFilteredReports(result, reportBody, timestamp, nameOfCheck, userName);
    }

    @RolesAllowed("reader")
    @GetMapping("/reports/paged")
    @ResponseBody
    public Page<ReportViewDTO> getPagedReports(@RequestParam("pageNumber") int pageNumber,
                                               @RequestParam(value = "sortingProperty", required = false) String sortingProperty) {
        return sortingProperty == null ?
                reportFacade.findByPageNumber(pageNumber) :
                reportFacade.findByPageNumberAndSortingProperty(pageNumber, sortingProperty);
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
