package com.sciamus.contractanalyzer.reporting.suites;


import com.sciamus.contractanalyzer.control.CheckReportDTO;
import com.sciamus.contractanalyzer.control.CheckReportMapper;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SuiteReportMapper {

    @Autowired
    public SuiteReportMapper(CheckReportMapper checkReportMapper) {
        this.checkReportMapper = checkReportMapper;
    }

    CheckReportMapper checkReportMapper;

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
