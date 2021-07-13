package com.sciamus.contractanalyzer.infrastructure.port;

import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.infrastructure.adapter.RepositoryConfigurable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportsRepository extends MongoRepository<Report, String> {

    List<Report> findAllByNameOfCheck(String name);

}