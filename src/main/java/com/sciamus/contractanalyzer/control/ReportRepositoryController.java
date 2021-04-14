package com.sciamus.contractanalyzer.control;


import com.sciamus.contractanalyzer.reporting.ReportRepository;
import com.sciamus.contractanalyzer.reporting.TestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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


}
