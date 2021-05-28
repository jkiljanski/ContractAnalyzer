package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.reporting.suites.SuiteReport;
import com.sciamus.contractanalyzer.reporting.suites.SuiteReportMapper;
import com.sciamus.contractanalyzer.suites.SuitesRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@RestController
public class SuitesRepositoryController {


    SuitesRepository suitesRepository;
    SuiteReportMapper mapper;

    public SuitesRepositoryController(SuitesRepository suitesRepository, SuiteReportMapper mapper) {
        this.suitesRepository = suitesRepository;
        this.mapper = mapper;
    }


    @RolesAllowed({"writer"})
    @PostMapping("/suites/{name}/run")
    @ResponseBody
    SuiteReport runSuite(@PathVariable("name") String name, @RequestParam(name = "url") String url) {
        return suitesRepository.runSuiteAndAddToRepository(name, url);
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
        return suitesRepository.getAllReports();
    }


}
