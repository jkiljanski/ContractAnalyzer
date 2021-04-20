package com.sciamus.contractanalyzer.control;


import com.sciamus.contractanalyzer.reporting.ReportNotFoundException;
import com.sciamus.contractanalyzer.reporting.ReportService;
import com.sciamus.contractanalyzer.reporting.TestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReportRepositoryController {

    private final ReportService reportService;

    @Autowired
    public ReportRepositoryController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping("/reports/{id}")
    @ResponseBody
    public TestReport GetReportById(
            @PathVariable("id") long id) {
//        System.out.println("# of reports: " + reportRepositoryImpl.getCountOfReports());
        return reportService.getReportByID(id);
    }


    @GetMapping("/reports")
    @ResponseBody
    public List<TestReport> getAllReports() {
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
