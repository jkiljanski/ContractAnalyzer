package com.sciamus.contractanalyzer.checks;

import com.sciamus.contractanalyzer.reporting.checks.ReportService;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ContractChecksService {


    private final RestContractCheckRepository restContractCheckRepository;
    private final ReportService reportService;

    @Autowired
    public ContractChecksService(RestContractCheckRepository restContractCheckRepository, ReportService reportService) {
        this.restContractCheckRepository = restContractCheckRepository;
        this.reportService = reportService;
    }


    //TO REFACTOR:



    public CheckReport runAndGetSavedReportWithId(String name, String url) throws MalformedURLException {

        CheckReport toSave = restContractCheckRepository.runCheck(name, new URL(url));
        // TODO: czy nie trzeba zwalidować czy się zachował?
        reportService.addReportToRepository(toSave);
        return toSave;

    }
    //TO REFACTOR:

    public CheckReport runAndGetSavedAutogeneratedReportWithId(String name, String url) throws MalformedURLException {

        CheckReport toSave = restContractCheckRepository.runCheck(name, new URL(url)).addAutogenerated();
        reportService.addReportToRepository(toSave);

        return toSave;

    }

}
