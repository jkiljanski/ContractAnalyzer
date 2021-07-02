package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportDTO;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReport;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportService;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;

@Service
public class AggregatedChecksFacade {

    private final AggregatedReportService aggregatedReportService;

    public AggregatedChecksFacade(AggregatedReportService aggregatedReportService) {
        this.aggregatedReportService = aggregatedReportService;
    }

    public List<AggregatedReport> getAggregatedChecksReports() {
        return aggregatedReportService.getAggregatedChecksReports();
    }

    public AggregatedReportDTO runAndSaveAggregatedChecks(String name, List<String> namesOfChecks, String url) throws MalformedURLException {
        return aggregatedReportService.runAndSaveAggregatedChecks(name, namesOfChecks, url);
    }
}
