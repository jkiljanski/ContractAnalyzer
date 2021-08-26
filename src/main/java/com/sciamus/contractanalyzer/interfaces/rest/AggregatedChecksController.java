package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.application.AggregatedChecksFacade;
import com.sciamus.contractanalyzer.application.AggregatedReportViewDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.MalformedURLException;
import java.util.List;

@RestController
public class AggregatedChecksController {

    private final AggregatedChecksFacade aggregatedChecksFacade;

    public AggregatedChecksController(AggregatedChecksFacade aggregatedChecksFacade) {
        this.aggregatedChecksFacade = aggregatedChecksFacade;
    }

    @GetMapping("/aggregatedChecksReports")
    @ResponseBody
    public List<AggregatedReportViewDTO> getAggregatedChecksReports() {
        return aggregatedChecksFacade.getAggregatedChecksReports();
    }


    @CrossOrigin("*")
    @RolesAllowed("writer")
    @PostMapping("/aggregatedChecks/run")
    @ResponseBody
    public AggregatedReportViewDTO runAggregatedChecks(
            @RequestParam List<String> namesOfChecks,
            @RequestParam(name = "url") String url,
            @RequestParam(required = false, name = "name") String name) throws MalformedURLException {
        return aggregatedChecksFacade.runAndSaveAggregatedChecks(name, namesOfChecks, url);
    }
}
