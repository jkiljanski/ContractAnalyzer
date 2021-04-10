package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.check.ContractChecksService;
import com.sciamus.contractanalyzer.reporting.ReportResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
public class ContractChecksServiceController {

    private final ContractChecksService contractChecksService;

    @Autowired
    public ContractChecksServiceController(ContractChecksService contractChecksService) {
        this.contractChecksService = contractChecksService;
    }

    //    @RequestMapping(value = , method = RequestMethod.GET)
    @GetMapping("/checks/{name}/run")
    @ResponseBody
    public ReportResults runAndSeeIfPassed(
            @PathVariable("name") String name, @RequestParam(required = false, name = "krowa") String url) throws MalformedURLException {
        return contractChecksService.checkIfPassed(name, url);
    }

}
