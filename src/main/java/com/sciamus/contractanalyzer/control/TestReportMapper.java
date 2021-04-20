package com.sciamus.contractanalyzer.control;


import com.sciamus.contractanalyzer.reporting.ReportResults;
import com.sciamus.contractanalyzer.reporting.TestReport;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        return new TestReport(testReportDTO.id, ReportResults.valueOf(testReportDTO.result), testReportDTO.reportBody, java.sql.Timestamp.valueOf(testReportDTO.timestamp.toString()), testReportDTO.nameOfCheck);
    }

    public TestReportDTO mapToDTO(TestReport testReport) {
        return new TestReportDTO(testReport.getReportId(), testReport.getResult().toString(), testReport.getReportBody(), new Date(testReport.getTimestamp().getTime()));
    }


}
