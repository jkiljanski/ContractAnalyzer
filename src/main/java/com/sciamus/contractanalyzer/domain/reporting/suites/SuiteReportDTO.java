package com.sciamus.contractanalyzer.domain.reporting.suites;

import com.sciamus.contractanalyzer.application.CheckReportDTO;
import io.vavr.collection.List;
import lombok.Getter;
import lombok.Setter;


public class SuiteReportDTO {

    @Getter
    @Setter
    public List<CheckReportDTO> testReportDTOList;

    @Override
    public String toString() {
        return "SuiteReportDTO{" +
                "testReportDTOList=" + testReportDTOList +
                '}';
    }
}
