package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.reporting.aggregatedChecks.AggregatedChecksDTO;
import com.sciamus.contractanalyzer.reporting.aggregatedChecks.AggregatedChecksReport;
import com.sciamus.contractanalyzer.reporting.aggregatedChecks.AggregatedChecksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.MalformedURLException;
import java.util.List;

@RestController
public class AggregatedChecksController {

    private final AggregatedChecksService aggregatedChecksService;

    @Autowired
    public AggregatedChecksController(AggregatedChecksService aggregatedChecksService) {
        this.aggregatedChecksService = aggregatedChecksService;
    }

    @GetMapping("/aggregatedChecksReports")
    @ResponseBody
    public List<AggregatedChecksReport> getAggregatedChecksReports() {
        return aggregatedChecksService.getAggregatedChecksReports();
    }

    @RolesAllowed("writer")
    @PostMapping("/aggregatedChecks/run")
    @ResponseBody
    public AggregatedChecksDTO runAggregatedChecks(
            @RequestParam List<String> namesOfChecks,
            @RequestParam(name = "url") String url,
            @RequestParam(required = false, name = "name") String name) throws MalformedURLException {
        return aggregatedChecksService.runAndSaveAggregatedChecks(name, namesOfChecks, url);
    }
}
