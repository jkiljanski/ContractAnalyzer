package com.sciamus.contractanalyzer.domain.checks.suites.reports;

import com.sciamus.contractanalyzer.application.ReportDTO;
import io.vavr.collection.List;
import lombok.Getter;
import lombok.Setter;


public class SuiteReportDTO {

    @Getter
    @Setter
    public List<ReportDTO> testReportDTOList;

    @Override
    public String toString() {
        return "SuiteReportDTO{" +
                "testReportDTOList=" + testReportDTOList +
                '}';
    }
}
