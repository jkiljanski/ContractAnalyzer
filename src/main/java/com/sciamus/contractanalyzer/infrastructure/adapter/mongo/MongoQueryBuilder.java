package com.sciamus.contractanalyzer.infrastructure.adapter.mongo;

import com.sciamus.contractanalyzer.infrastructure.port.ReportFilterParametersInfrastructureDTO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MongoQueryBuilder {

    Query buildQuery(ReportFilterParametersInfrastructureDTO reportFilterParametersInfrastructureDTO) {

        Query query = new Query();

        Criteria andCriteria = new Criteria();

        if (reportFilterParametersInfrastructureDTO.result !=null) {
            query.addCriteria(Criteria.where("result").is(reportFilterParametersInfrastructureDTO.result));
        }
        if (reportFilterParametersInfrastructureDTO.reportBody !=null) {
            query.addCriteria(Criteria.where("content").regex(reportFilterParametersInfrastructureDTO.reportBody));
        }
        if( reportFilterParametersInfrastructureDTO.userName !=null) {
            query.addCriteria(Criteria.where("userName").is(reportFilterParametersInfrastructureDTO.userName));
        }
        if( reportFilterParametersInfrastructureDTO.nameOfCheck !=null) {
            query.addCriteria(Criteria.where("name").is(reportFilterParametersInfrastructureDTO.nameOfCheck));
        }

        Criteria from = null;
        Criteria to = null;

        if(reportFilterParametersInfrastructureDTO.timestampFrom !=null) {
            from = Criteria.where("timestamp").gte(reportFilterParametersInfrastructureDTO.timestampFrom);
        }

        if(reportFilterParametersInfrastructureDTO.timestampTo !=null) {
            to = Criteria.where("timestamp").lt(reportFilterParametersInfrastructureDTO.timestampTo);

        }

        if(from !=null && to !=null) {
            andCriteria.andOperator(from, to);
            query.addCriteria(andCriteria);
        }

        return query;

    }

}
