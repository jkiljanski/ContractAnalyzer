package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.check.ContractChecksService;
import com.sciamus.contractanalyzer.reporting.TestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@RestController
public class ContractChecksServiceController {

    private final ContractChecksService contractChecksService;

    @Autowired
    public ContractChecksServiceController(ContractChecksService contractChecksService) {
        this.contractChecksService = contractChecksService;
    }


    @RequestMapping(value = "/checks/{name}/run", method = RequestMethod.GET)
    @ResponseBody
    public boolean runAndSeeIfPassed(
            @PathVariable("name") String name, URL url) {
        return contractChecksService.checkIfPassed(name, url);
    }

}
