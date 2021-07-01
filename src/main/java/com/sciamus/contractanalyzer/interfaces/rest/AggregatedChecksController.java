package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.application.AggregatedCheckFacade;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedChecksDTO;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedChecksReport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.MalformedURLException;
import java.util.List;

@RestController
public class AggregatedChecksController {

    private final AggregatedCheckFacade aggregatedCheckFacade;

    public AggregatedChecksController(AggregatedCheckFacade aggregatedCheckFacade) {
        this.aggregatedCheckFacade = aggregatedCheckFacade;
    }

    @GetMapping("/aggregatedChecksReports")
    @ResponseBody
    public List<AggregatedChecksReport> getAggregatedChecksReports() {
        return aggregatedCheckFacade.getAggregatedChecksReports();
    }

    @RolesAllowed("writer")
    @PostMapping("/aggregatedChecks/run")
    @ResponseBody
    public AggregatedChecksDTO runAggregatedChecks(
            @RequestParam List<String> namesOfChecks,
            @RequestParam(name = "url") String url,
            @RequestParam(required = false, name = "name") String name) throws MalformedURLException {
        return aggregatedCheckFacade.runAndSaveAggregatedChecks(name, namesOfChecks, url);
    }
}
