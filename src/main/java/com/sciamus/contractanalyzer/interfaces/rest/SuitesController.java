package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.domain.reporting.suites.SuiteReport;
import com.sciamus.contractanalyzer.domain.reporting.suites.SuiteReportMapper;
import com.sciamus.contractanalyzer.domain.suites.SuitesRepository;
import com.sciamus.contractanalyzer.domain.suites.SuitesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@RestController
public class SuitesController {

    @Autowired
    private SuitesService suitesService;

    @Autowired
    private SuitesRepository suitesRepository;

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
