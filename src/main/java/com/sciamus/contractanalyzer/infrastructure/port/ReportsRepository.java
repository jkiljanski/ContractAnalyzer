package com.sciamus.contractanalyzer.infrastructure.port;

import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportResults;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ReportsRepository extends MongoRepository<Report, String> {

    List<Report> findAllByNameOfCheck(String name);

    List<Report> findReportsByIdIsWithin(String idFrom, String idTo);

    List<Report> findByResultContaining(ReportResults result);

    List<Report> findByReportBodyContaining(String reportBody);

    List<Report> findByTimestampContaining(Date timestamp);

    List<Report> findByNameOfCheckContaining(String result);

    List<Report> findByUserNameContaining(String result);
}
