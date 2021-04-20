package com.sciamus.contractanalyzer.reporting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    private final ReportRepository repository;

    private long nextID;

    public IdGenerator(ReportRepository repository) {
        this.repository = repository;
    }
    // the method should check repository count every time since there can be db drops during the runtime of the application

    long getNextID() {

        nextID = repository.count() +1;
        return nextID;

    }


}
