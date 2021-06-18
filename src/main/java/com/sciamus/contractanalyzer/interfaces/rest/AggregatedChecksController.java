package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks.AggregatedChecksDTO;
import com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks.AggregatedChecksReport;
import com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks.AggregatedChecksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.MalformedURLException;
import java.util.List;

@RestController
public class AggregatedChecksController {

    @Autowired
    private AggregatedChecksService aggregatedChecksService;

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
