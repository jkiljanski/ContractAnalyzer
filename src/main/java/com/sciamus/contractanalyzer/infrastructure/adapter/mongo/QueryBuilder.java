package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.application.ReportFilterParameters;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class QueryBuilder {

    Query buildQuery(ReportFilterParameters reportFilterParameters) {

        Query query = new Query();

        query.addCriteria(Criteria.where(""));

        return  query;

    }


}
