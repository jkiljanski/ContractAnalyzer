package com.sciamus.contractanalyzer.control;


import com.sciamus.contractanalyzer.reporting.ReportResults;
import com.sciamus.contractanalyzer.reporting.TestReport;
import com.sciamus.contractanalyzer.reporting.TestReportBuilder;
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

    public TestReport mapFromDTO(TestReportDTO testReportDTO) {
        return new TestReportBuilder().setId(testReportDTO.id)
                .setResult(ReportResults.valueOf(testReportDTO.result))
                .setReportBody(testReportDTO.reportBody)
                .setTimestamp(testReportDTO.timestamp)
                .setNameOfCheck(testReportDTO.nameOfCheck)
                .createTestReport();
    }

    public TestReportDTO mapToDTO(TestReport testReport) {
        return new TestReportDTO(testReport.getId(), testReport.getResult().toString(), testReport.getReportBody(), testReport.getTimestamp(), testReport.getNameOfCheck());
    }


}
