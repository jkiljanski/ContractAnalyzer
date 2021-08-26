package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MongoReportsRepository extends MongoRepository<ReportDocument, String> {

    List<ReportDocument> findAllByNameOfCheck(String name);

    List<ReportDocument> findByReportBodyContaining(String reportBody);


    List<ReportDocument> findAllByTimestampBetween(LocalDateTime timestampFrom);

    List<ReportDocument> findByNameOfCheckContaining(String result);

    List<ReportDocument> findByUserNameContaining(String result);
}
