package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.application.ReportFilterParameters;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;

public class QueryBuilder {

    Query buildQuery(ReportFilterParameters reportFilterParameters) {

        Query query = new Query();
        Criteria andCriteria = new Criteria();

        query.addCriteria(Criteria.where("result").is(reportFilterParameters.getResult()));
        query.addCriteria(Criteria.where("content").regex(reportFilterParameters.getReportBody()));

        query.addCriteria(Criteria.where("name").is(reportFilterParameters.getNameOfCheck()));
        query.addCriteria(Criteria.where("userName").is(reportFilterParameters.getUserName()));
        LocalDateTime from = LocalDateTime.parse(reportFilterParameters.getTimestampFrom());
        LocalDateTime to = LocalDateTime.parse(reportFilterParameters.getTimestampTo());

        andCriteria.andOperator(Criteria.where("timestamp").gte(from), Criteria.where("timestamp").lt(to));
        query.addCriteria(andCriteria);

        return query;

    }

}
