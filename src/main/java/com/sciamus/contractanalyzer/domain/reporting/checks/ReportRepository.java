package com.sciamus.contractanalyzer.domain.reporting.checks;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<CheckReport, String> {

    List<CheckReport> findAllByNameOfCheck(String name);



}
