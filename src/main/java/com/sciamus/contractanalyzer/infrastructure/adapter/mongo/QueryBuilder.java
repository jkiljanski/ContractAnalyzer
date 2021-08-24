package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.application.ReportFilterParameters;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;

public class QueryBuilder {

    Query buildQuery(ReportFilterParameters reportFilterParameters) {

        Query query = new Query();
        Criteria andCriteria = new Criteria();

        query.addCriteria(Criteria.where("result").regex(reportFilterParameters.result.getOrElse("")));
        query.addCriteria(Criteria.where("content").regex(reportFilterParameters.reportBody.getOrElse("")));

        query.addCriteria(Criteria.where("name").regex(reportFilterParameters.nameOfCheck.getOrElse("")));
        query.addCriteria(Criteria.where("userName").regex(reportFilterParameters.userName.getOrElse("")));

        LocalDateTime from = LocalDateTime.parse(reportFilterParameters.timestampFrom.getOrElse("1300-08-24T07:34:09.013"));
        LocalDateTime to = LocalDateTime.parse(reportFilterParameters.timestampTo.getOrElse("3200-08-24T07:34:09.013"));

        andCriteria.andOperator(Criteria.where("timestamp").gte(from),
                Criteria.where("timestamp").lt(to));
        query.addCriteria(andCriteria);

        return query;

    }


}
