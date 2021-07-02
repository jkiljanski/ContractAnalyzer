package com.sciamus.contractanalyzer.infrastructure.port;

import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<Report, String>, RepositoryConfigurable {

    List<Report> findAllByNameOfCheck(String name);



}
