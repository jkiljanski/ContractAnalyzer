package com.sciamus.contractanalyzer.application.mapper;


import com.sciamus.contractanalyzer.application.ReportDTO;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;

public class ReportMapper {

    private final CurrentUserService currentUserService;

    public ReportMapper(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }


//    private TestReport testReport;
//    private TestReportDTO testReportDTO;
//
//
//
//    public TestReportMapper(TestReport testReport, TestReportDTO testReportDTO) {
//        this.testReport = testReport;
//        this.testReportDTO = testReportDTO;
//    }

    //review pls:

    public Report mapFromDTO(ReportDTO reportDTO) {
        return new ReportBuilder().setId(reportDTO.id)
                .setResult(ReportResults.valueOf(reportDTO.result))
                .setReportBody(reportDTO.reportBody)
                .setTimestamp(reportDTO.timestamp)
                .setNameOfCheck(reportDTO.nameOfCheck)
                .setUserName(currentUserService.obtainUserName())
                .build();
    }

    public ReportDTO mapToDTO(Report report) {
        return new ReportDTO(report.getId(), report.getResult().toString(), report.getReportBody(), report.getTimestamp(), report.getNameOfCheck(), report.getUserName());
    }


}
