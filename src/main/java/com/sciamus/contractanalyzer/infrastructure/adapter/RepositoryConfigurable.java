package com.sciamus.contractanalyzer.infrastructure.adapter;

import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportsRepository;
import com.sciamus.contractanalyzer.infrastructure.port.ReportsRepository;
import com.sciamus.contractanalyzer.infrastructure.port.SuitesReportsRepository;

public interface RepositoryConfigurable {


    AggregatedReportsRepository getAggregatedReportsRepository();

    ReportsRepository getReportsRepository();

    SuitesReportsRepository getSuitesReportsRepository();



}
