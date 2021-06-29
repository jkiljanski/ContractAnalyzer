package com.sciamus.contractanalyzer.domain.reporting.idGenerator;

import com.sciamus.contractanalyzer.domain.reporting.checks.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ReportIdGenerator {

    private final ReportRepository repository;

    private String nextID;

    @Autowired
    public ReportIdGenerator(ReportRepository repository) {
        this.repository = repository;
    }

    // the method should check repository count every time since there can be db drops during the runtime of the application

    public String getNextID() {

        nextID = repository.count() + 1 + "";
        return nextID;

    }


}
