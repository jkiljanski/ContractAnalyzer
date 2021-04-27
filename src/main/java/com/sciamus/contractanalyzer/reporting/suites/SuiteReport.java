package com.sciamus.contractanalyzer.reporting.suites;

import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "checkSuiteReports")
@TypeAlias("suite_report")
public class SuiteReport {



    @PersistenceConstructor
    public SuiteReport(List<CheckReport> reportList) {
        this.reportList = reportList;
    }

    @DBRef
    final private List<CheckReport> reportList;

    public io.vavr.collection.List<CheckReport> getReportList() {
        return io.vavr.collection.List.ofAll(reportList);
    }

    @Override
    public String toString() {
        return "SuiteReport{" +
                "reportList=" + reportList.toString() +
                '}';
    }


}
