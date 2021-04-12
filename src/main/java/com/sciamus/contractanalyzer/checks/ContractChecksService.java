package com.sciamus.contractanalyzer.checks;

import com.sciamus.contractanalyzer.reporting.ReportResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ContractChecksService {


    private final RestContractCheckRepository restContractCheckRepository;

    @Autowired
    public ContractChecksService(RestContractCheckRepository restContractCheckRepository) {
        this.restContractCheckRepository = restContractCheckRepository;
    }


    public ReportResults checkIfPassed (String name, String url) throws MalformedURLException {

        System.out.println("Called URL: "+url);
        return restContractCheckRepository.runCheck(name, new URL(url)).isPassed();

    }



}
