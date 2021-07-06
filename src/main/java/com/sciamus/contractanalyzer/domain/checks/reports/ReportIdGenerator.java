package com.sciamus.contractanalyzer.domain.checks.reports;

import com.sciamus.contractanalyzer.infrastructure.port.ReportsRepository;

public class ReportIdGenerator {

    private final ReportsRepository repository;

    private String nextID;

    public ReportIdGenerator(ReportsRepository repository) {
        this.repository = repository;
    }

    // the method should check repository count every time since there can be db drops during the runtime of the application

    public String getNextID() {

        nextID = repository.count() + 1 + "";
        return nextID;

    }


}
