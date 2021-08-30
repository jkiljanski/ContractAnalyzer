package com.sciamus.contractanalyzer.interfaces.rest;


import com.sciamus.contractanalyzer.application.ReportFacade;
import com.sciamus.contractanalyzer.application.ReportFilterParameters;
import com.sciamus.contractanalyzer.application.ReportViewDTO;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
public class ReportController {

    private final ReportFacade reportFacade;

    public ReportController(ReportFacade reportFacade) {
        this.reportFacade = reportFacade;
    }


    @RolesAllowed("reader")
    @GetMapping("/reports/{id}")
    @ResponseBody
    public ReportViewDTO getReportById(
            @PathVariable("id") String id) {
        return reportFacade.getReportByID(id);
    }

    @RolesAllowed("reader")
    @GetMapping("/filteredReports")
    @ResponseBody
    public Page<ReportViewDTO> getFileteredReports(@RequestParam(value = "result", required = false) String result,
                                                   @RequestParam(value = "reportBody", required = false) String reportBody,
                                                   @RequestParam(value = "timestampFrom", required = false) String timestampFrom,
                                                   @RequestParam(value = "timestampTo", required = false) String timestampTo,
                                                   @RequestParam(value = "nameOfCheck", required = false) String nameOfCheck,
                                                   @RequestParam(value = "userName", required = false) String userName,
                                                   @RequestParam(value = "pageNumber", required = false) int number) {
        return reportFacade.getFilteredReports(new ReportFilterParameters(result, reportBody, convertDateToDatetime(timestampFrom), convertDateToDatetime(timestampTo), nameOfCheck, userName), number);
    }

    @RolesAllowed("reader")
    @GetMapping("/reports/paged")
    @ResponseBody
    public Page<ReportViewDTO> getPagedReports(@RequestParam("pageNumber") int pageNumber,
                                               @RequestParam(value = "sortingProperty", required = false) String sortingProperty,
                                               @RequestParam(value = "order", required = false) String sortingOrder) {
        return sortingProperty == null ?
                reportFacade.findAllByPageNumber(pageNumber) :
                reportFacade.findAllByPageNumberAndSortingProperty(pageNumber, sortingProperty, sortingOrder);
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


    private String convertDateToDatetime(String timestamp) {

        if (timestamp.length()<12) {

            String tstmp = timestamp + "00:00";
            System.out.println(tstmp);
            return tstmp;
        }
        return  timestamp;
    }

}
