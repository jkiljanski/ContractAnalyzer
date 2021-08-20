package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MongoReportsRepository extends MongoRepository<ReportDocument, String> {

    @Query("{$or:[{ $expr: { $eq: ['?0', 'null'] },{'result': ?0}]}" +
            "{$or:[{ $expr: { $eq: ['?1', 'null'] },{'content':{$regex : ?1}}]}" +
            "{$or:[{ $expr: { $eq: ['?2', 'null'] },{ $expr: { $eq: ['?3', 'null'] },{'timestamp' : {$gte : ?2, $lt : ?3 }]}" +
            "{$or:[{ $expr: { $eq: ['?4', 'null'] }, { 'name': ?4 } ]}" +
            "{$or:[{ $expr: { $eq: ['?5', 'null'] }, { 'userName': ?5 } ]}")
    Page<ReportDocument> findAll(String result, String reportBody, String timestampFrom, String timestampTo, String nameOfCheck, String userName, Pageable pageable);

}


//{ $and: [