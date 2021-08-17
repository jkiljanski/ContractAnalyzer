package com.sciamus.contractanalyzer.domain.checks.suites.reports;


import com.sciamus.contractanalyzer.application.ReportViewDTO;
import com.sciamus.contractanalyzer.application.mapper.report.ReportViewMapper;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;


public class SuiteReportMapper {

    private final ReportViewMapper reportViewMapper;

    @Autowired
    public SuiteReportMapper(ReportViewMapper reportViewMapper) {
        this.reportViewMapper = reportViewMapper;
    }

    public SuiteReport mapFromDTO (SuiteReportDTO suiteReportDTO) {

        List<Report> checkReportList = suiteReportDTO.testReportDTOList.map(reportViewMapper::mapFromDTO);

        return new SuiteReport(checkReportList.toJavaList());
    }

    public SuiteReportDTO mapToDTO (SuiteReport suiteReport) {

        List<ReportViewDTO> testReportDTOList = suiteReport.getReportList().map(reportViewMapper::mapToDTO);

        SuiteReportDTO suiteReportDTO = new SuiteReportDTO();

        suiteReportDTO.testReportDTOList = testReportDTOList;

        return suiteReportDTO;
    }


}
