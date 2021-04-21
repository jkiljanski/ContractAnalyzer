package com.sciamus.contractanalyzer.reporting;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<TestReport, String> {

    List<TestReport> findAllByNameOfCheck(String name);



}
