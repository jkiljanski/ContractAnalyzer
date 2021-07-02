package com.sciamus.contractanalyzer.domain.checks.suites.reports;


import com.sciamus.contractanalyzer.application.ReportDTO;
import com.sciamus.contractanalyzer.application.mapper.ReportMapper;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;


public class SuiteReportMapper {

    private final ReportMapper reportMapper;

    @Autowired
    public SuiteReportMapper(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }

    public SuiteReport mapFromDTO (SuiteReportDTO suiteReportDTO) {

        List<Report> checkReportList = suiteReportDTO.testReportDTOList.map(reportMapper::mapFromDTO);

        return new SuiteReport(checkReportList.toJavaList());
    }

    public SuiteReportDTO mapToDTO (SuiteReport suiteReport) {

        List<ReportDTO> testReportDTOList = suiteReport.getReportList().map(reportMapper::mapToDTO);

        SuiteReportDTO suiteReportDTO = new SuiteReportDTO();

        suiteReportDTO.testReportDTOList = testReportDTOList;

        return suiteReportDTO;
    }


}
