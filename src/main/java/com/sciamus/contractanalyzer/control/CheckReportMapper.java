package com.sciamus.contractanalyzer.control;


import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.CheckReportBuilder;
import org.springframework.stereotype.Component;

@Component
public class CheckReportMapper {

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
                .createTestReport();
    }

    public CheckReportDTO mapToDTO(CheckReport checkReport) {
        return new CheckReportDTO(checkReport.getId(), checkReport.getResult().toString(), checkReport.getReportBody(), checkReport.getTimestamp(), checkReport.getNameOfCheck());
    }


}
