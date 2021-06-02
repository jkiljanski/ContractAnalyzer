package com.sciamus.contractanalyzer.control;


import com.sciamus.contractanalyzer.checks.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import org.springframework.stereotype.Component;

@Component
public class CheckReportMapper {
    private final CurrentUserService currentUserService;

    public CheckReportMapper(CurrentUserService currentUserService) {
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

    public CheckReport mapFromDTO(CheckReportDTO checkReportDTO) {
        return new CheckReportBuilder().setId(checkReportDTO.id)
                .setResult(ReportResults.valueOf(checkReportDTO.result))
                .setReportBody(checkReportDTO.reportBody)
                .setTimestamp(checkReportDTO.timestamp)
                .setNameOfCheck(checkReportDTO.nameOfCheck)
                .setUserName(currentUserService.obtainUserName())
                .build();
    }

    public CheckReportDTO mapToDTO(CheckReport checkReport) {
        return new CheckReportDTO(checkReport.getId(), checkReport.getResult().toString(), checkReport.getReportBody(), checkReport.getTimestamp(), checkReport.getNameOfCheck(), checkReport.getUserName());
    }


}
