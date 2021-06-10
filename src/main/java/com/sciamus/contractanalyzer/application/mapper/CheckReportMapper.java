package com.sciamus.contractanalyzer.application.mapper;


import com.sciamus.contractanalyzer.domain.checks.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.application.CheckReportDTO;
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
