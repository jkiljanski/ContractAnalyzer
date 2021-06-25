package com.sciamus.contractanalyzer.domain.reporting.suites;


import com.sciamus.contractanalyzer.application.CheckReportDTO;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;


public class SuiteReportMapper {

    private final CheckReportMapper checkReportMapper;

    @Autowired
    public SuiteReportMapper(CheckReportMapper checkReportMapper) {
        this.checkReportMapper = checkReportMapper;
    }

    public SuiteReport mapFromDTO (SuiteReportDTO suiteReportDTO) {

        List<CheckReport> checkReportList = suiteReportDTO.testReportDTOList.map(checkReportMapper::mapFromDTO);

        return new SuiteReport(checkReportList.toJavaList());
    }

    public SuiteReportDTO mapToDTO (SuiteReport suiteReport) {

        List<CheckReportDTO> testReportDTOList = suiteReport.getReportList().map(checkReportMapper::mapToDTO);

        SuiteReportDTO suiteReportDTO = new SuiteReportDTO();

        suiteReportDTO.testReportDTOList = testReportDTOList;

        return suiteReportDTO;
    }


}
