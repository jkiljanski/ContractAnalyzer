package com.sciamus.contractanalyzer.domain.checks.suites.reports;

import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "checkSuiteReports")
@TypeAlias("suite_report")
public class SuiteReport {



    @PersistenceConstructor
    public SuiteReport(List<Report> reportList) {
        this.reportList = reportList;
    }

    @DBRef
    final private List<Report> reportList;

    public io.vavr.collection.List<Report> getReportList() {
        return io.vavr.collection.List.ofAll(reportList);
    }

    @Override
    public String toString() {
        return "SuiteReport{" +
                "reportList=" + reportList.toString() +
                '}';
    }


}
