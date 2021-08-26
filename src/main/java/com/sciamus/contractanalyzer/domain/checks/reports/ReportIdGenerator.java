package com.sciamus.contractanalyzer.domain.checks.reports;

import com.sciamus.contractanalyzer.infrastructure.adapter.mongo.MongoReportsRepository;

public class ReportIdGenerator {

    private final MongoReportsRepository repository;

    private String nextID;

    public ReportIdGenerator(MongoReportsRepository repository) {
        this.repository = repository;
    }

    // the method should check repository count every time since there can be db drops during the runtime of the application

    public String getNextID() {
        nextID = Long.toString(repository.count() + 1);
        return nextID;
    }


}
