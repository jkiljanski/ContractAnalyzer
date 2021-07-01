package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedChecksDTO;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedChecksReport;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedChecksService;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;

@Service
public class AggregatedCheckFacade {

    private final AggregatedChecksService aggregatedChecksService;

    public AggregatedCheckFacade(AggregatedChecksService aggregatedChecksService) {
        this.aggregatedChecksService = aggregatedChecksService;
    }

    public List<AggregatedChecksReport> getAggregatedChecksReports() {
        return aggregatedChecksService.getAggregatedChecksReports();
    }

    public AggregatedChecksDTO runAndSaveAggregatedChecks(String name, List<String> namesOfChecks, String url) throws MalformedURLException {
        return aggregatedChecksService.runAndSaveAggregatedChecks(name, namesOfChecks, url);
    }
}
