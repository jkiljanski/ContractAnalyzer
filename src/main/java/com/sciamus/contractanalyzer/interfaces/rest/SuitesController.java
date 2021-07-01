package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.domain.checks.suites.reports.SuiteReport;
import com.sciamus.contractanalyzer.domain.checks.suites.SuitesRepository;
import com.sciamus.contractanalyzer.domain.checks.suites.SuitesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class SuitesController {

    //TODO: fix me: this cannot use domain object directly -> it always must got through application layer
    private final SuitesService suitesService;

    //TODO: fix me: this cannot use domain object directly -> it always must got through application layer
    private final SuitesRepository suitesRepository;

    @Autowired
    public SuitesController(SuitesService suitesService, SuitesRepository suitesRepository) {
        this.suitesService = suitesService;
        this.suitesRepository = suitesRepository;
    }

    @RolesAllowed({"writer"})
    @PostMapping("/suites/{name}/run")
    @ResponseBody
    SuiteReport runSuite(@PathVariable("name") String name, @RequestParam(name = "url") String url) {
        return suitesService.runSuiteAndAddToRepository(name, url);
    }

    @RolesAllowed("reader")
    @GetMapping("/suites")
    @ResponseBody
    List<String> getAllSuites() {
        return suitesRepository.getAllSuites();
    }

    @RolesAllowed("reader")
    @GetMapping("/suites/reports")
    @ResponseBody
    List<SuiteReport> getAllReports() {
        return suitesService.getAllReports();
    }

}
