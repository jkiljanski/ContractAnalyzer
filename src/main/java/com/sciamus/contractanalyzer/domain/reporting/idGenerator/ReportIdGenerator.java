package com.sciamus.contractanalyzer.domain.reporting.idGenerator;

import com.sciamus.contractanalyzer.domain.reporting.checks.ReportRepository;
import org.springframework.stereotype.Component;

@Component
public class ReportIdGenerator {

    private final ReportRepository repository;

    private String nextID;

    public ReportIdGenerator(ReportRepository repository) {
        this.repository = repository;
    }
    // the method should check repository count every time since there can be db drops during the runtime of the application

    public String getNextID() {

        nextID = repository.count() + 1 + "";
        return nextID;

    }


}