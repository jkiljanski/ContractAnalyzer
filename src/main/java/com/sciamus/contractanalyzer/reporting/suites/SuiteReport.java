package com.sciamus.contractanalyzer.reporting;

import io.vavr.collection.List;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "checkSuiteReports")
@TypeAlias("suite_report")
public class SuiteReport {

    private List<CheckReport> reportList;

    public SuiteReport(List<CheckReport> reportList) {
        this.reportList = reportList;
    }

    public SuiteReport() {

    }

    public List <CheckReport> addReport(CheckReport checkReport) {
        return reportList.append(checkReport);
    }


}
