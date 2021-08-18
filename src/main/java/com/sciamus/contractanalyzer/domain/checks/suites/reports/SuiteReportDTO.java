package com.sciamus.contractanalyzer.domain.checks.suites.reports;

import com.sciamus.contractanalyzer.application.ReportViewDTO;
import io.vavr.collection.List;
import lombok.Getter;
import lombok.Setter;


public class SuiteReportDTO {

    @Getter
    @Setter
    public List<ReportViewDTO> testReportDTOList;

    @Override
    public String toString() {
        return "SuiteReportDTO{" +
                "testReportDTOList=" + testReportDTOList +
                '}';
    }
}
