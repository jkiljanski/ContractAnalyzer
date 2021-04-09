package com.sciamus.contractanalyzer.check;

import com.sciamus.contractanalyzer.reporting.ReportResults;
import com.sciamus.contractanalyzer.reporting.TestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
public class ContractChecksService {


    //czy to dobry pomysł
    URL serverURL;


    private final RestContractCheckRepository restContractCheckRepository;

    @Autowired
    public ContractChecksService(RestContractCheckRepository restContractCheckRepository) {
        this.restContractCheckRepository = restContractCheckRepository;
    }




    public boolean checkIfPassed (String name, URL url) {

        return restContractCheckRepository.runCheck(name, serverURL).isPassed();

    }

}
