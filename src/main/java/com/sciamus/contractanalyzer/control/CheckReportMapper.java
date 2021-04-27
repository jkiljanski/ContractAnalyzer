package com.sciamus.contractanalyzer.control;


import com.sciamus.contractanalyzer.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.CheckReportBuilder;
import org.springframework.stereotype.Component;

@Component
public class TestReportMapper {

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

    public CheckReport mapFromDTO(TestReportDTO testReportDTO) {
        return new CheckReportBuilder().setId(testReportDTO.id)
                .setResult(ReportResults.valueOf(testReportDTO.result))
                .setReportBody(testReportDTO.reportBody)
                .setTimestamp(testReportDTO.timestamp)
                .setNameOfCheck(testReportDTO.nameOfCheck)
                .createTestReport();
    }

    public TestReportDTO mapToDTO(CheckReport checkReport) {
        return new TestReportDTO(checkReport.getId(), checkReport.getResult().toString(), checkReport.getReportBody(), checkReport.getTimestamp(), checkReport.getNameOfCheck());
    }


}
