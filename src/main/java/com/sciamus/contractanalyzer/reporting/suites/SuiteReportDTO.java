package com.sciamus.contractanalyzer.reporting.suites;

import com.sciamus.contractanalyzer.control.CheckReportDTO;
import io.vavr.collection.List;


public class SuiteReportDTO {


    public List<CheckReportDTO> testReportDTOList;

    public List<CheckReportDTO> getTestReportDTOList() {
        return testReportDTOList;
    }

    @Override
    public String toString() {
        return "SuiteReportDTO{" +
                "testReportDTOList=" + testReportDTOList +
                '}';
    }

    public void setTestReportDTOList(List<CheckReportDTO> testReportDTOList) {
        this.testReportDTOList = testReportDTOList;
    }
}
