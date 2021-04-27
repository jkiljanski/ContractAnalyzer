package com.sciamus.contractanalyzer.reporting.checks;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    private final ReportRepository repository;

    private String nextID;

    public IdGenerator(ReportRepository repository) {
        this.repository = repository;
    }
    // the method should check repository count every time since there can be db drops during the runtime of the application

    String getNextID() {

        nextID = repository.count() + 1 + "";
        return nextID;

    }


}
