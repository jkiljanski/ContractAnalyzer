package com.sciamus.contractanalyzer.interfaces.rest;


import com.sciamus.contractanalyzer.application.QueryParameters;
import com.sciamus.contractanalyzer.application.ReportFacade;
import com.sciamus.contractanalyzer.application.ReportViewDTO;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
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
    public List<ReportViewDTO> getPagedReports(@RequestParam("documentsPerPage") int documentsPerPage) {
        return reportFacade.findByPageSize(documentsPerPage);
    }

    @RolesAllowed("reader")
    @GetMapping("/reports/filter")
    @ResponseBody

    //parameter object - refactor w idei <3
    public List<ReportViewDTO> getByTimestamp(@RequestParam(value = "dateTimeFrom", required = false) String from,
                                              @RequestParam(value = "dateTimeTo", required = false) String to,
                                              @RequestParam(value = "nameOfCheck", required = false) String name,
                                              @RequestParam(value = "result", required = false) String result,
                                              @RequestParam(value = "userName", required = false) String username,
                                              @RequestParam(value = "reportBody", required = false) String reportBody
                                              ) {


        return reportFacade.filterAll(new QueryParameters(LocalDateTime.parse(from), LocalDateTime.parse(to), name, result, username, reportBody));

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
