package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.application.ReportFilterParameters;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class QueryBuilder {

    Query buildQuery(ReportFilterParameters reportFilterParameters) {

        Query query = new Query();

        Criteria andCriteria = new Criteria();

        if (reportFilterParameters.result !=null && !reportFilterParameters.result.equals("")) {
            query.addCriteria(Criteria.where("result").is(reportFilterParameters.result));
        }
        if (reportFilterParameters.reportBody !=null &&!reportFilterParameters.result.equals("")) {
            query.addCriteria(Criteria.where("content").regex(reportFilterParameters.reportBody));
        }
        if( reportFilterParameters.userName !=null &&!reportFilterParameters.userName.equals("")) {
            query.addCriteria(Criteria.where("userName").is(reportFilterParameters.userName));
        }
        if( reportFilterParameters.nameOfCheck !=null &&!reportFilterParameters.nameOfCheck.equals("")) {
            query.addCriteria(Criteria.where("name").is(reportFilterParameters.nameOfCheck));
        }

        Criteria from = null;
        Criteria to = null;

        if(reportFilterParameters.timestampFrom !=null &&!reportFilterParameters.timestampFrom.equals("")) {
            if (reportFilterParameters.timestampFrom.length()>10) {
                LocalDateTime parsed = LocalDateTime.parse(reportFilterParameters.timestampFrom);
                from = Criteria.where("timestamp").gte(parsed);
            }
            else {
                LocalDate parsed = LocalDate.parse(reportFilterParameters.timestampFrom);
                from = Criteria.where("timestamp").gte(parsed);
            }
        }

        if(reportFilterParameters.timestampTo !=null && !reportFilterParameters.timestampTo.equals("") ) {
            if (reportFilterParameters.timestampTo.length()>10) {
                to = Criteria.where("timestamp").lte(LocalDateTime.parse(reportFilterParameters.timestampTo));
            }
            else {
                to = Criteria.where("timestamp").lte(LocalDate.parse(reportFilterParameters.timestampTo));
            }
        }

        if(from !=null && to !=null) {
            andCriteria.andOperator(from, to);
            query.addCriteria(andCriteria);
        }

        return query;

    }

}
