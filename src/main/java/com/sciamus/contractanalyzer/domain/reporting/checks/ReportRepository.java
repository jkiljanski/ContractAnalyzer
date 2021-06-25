package com.sciamus.contractanalyzer.domain.reporting.checks;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<CheckReport, String> {

    List<CheckReport> findAllByNameOfCheck(String name);



}