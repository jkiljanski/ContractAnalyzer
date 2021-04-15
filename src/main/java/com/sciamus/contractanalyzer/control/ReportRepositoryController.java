package com.sciamus.contractanalyzer.control;


import com.sciamus.contractanalyzer.checks.CheckNotFoundException;
import com.sciamus.contractanalyzer.reporting.ReportNotFoundException;
import com.sciamus.contractanalyzer.reporting.ReportRepository;
import com.sciamus.contractanalyzer.reporting.TestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportRepositoryController {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportRepositoryController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    @GetMapping("/reports/{id}")
    @ResponseBody
    public TestReport GetReportById(
            @PathVariable("id") long id) {
        System.out.println("# of reports: " + reportRepository.getCountOfReports());
        return reportRepository.getReportByID(id);
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
