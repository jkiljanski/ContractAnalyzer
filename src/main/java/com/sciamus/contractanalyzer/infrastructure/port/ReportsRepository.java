package com.sciamus.contractanalyzer.infrastructure.port;

import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportsRepository extends MongoRepository<Report, String> {

    List<Report> findAllByNameOfCheck(String name);

    Page<Report> findTopById(Pageable pageable);

    List<Report> findReportsByIdIsWithin(String idFrom, String idTo);

}
