package com.sciamus.contractanalyzer.checks;

import com.sciamus.contractanalyzer.reporting.ReportService;
import com.sciamus.contractanalyzer.reporting.TestReport;
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

    public TestReport runAndGetSavedReportWithId(String name, String url) throws MalformedURLException {

        System.out.println("Called URL: " + url);

        TestReport toSave = restContractCheckRepository.runCheck(name, new URL(url));
        // TODO: czy nie trzeba zwalidować czy się zachował?
        reportService.addReportToRepository(toSave);

        return toSave;

    }


}
