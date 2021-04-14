package com.sciamus.contractanalyzer.checks;

import com.sciamus.contractanalyzer.reporting.ReportRepository;
import com.sciamus.contractanalyzer.reporting.TestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Service
public class ContractChecksService {


    private final RestContractCheckRepository restContractCheckRepository;
    private final ReportRepository reportRepository;

    @Autowired
    public ContractChecksService(RestContractCheckRepository restContractCheckRepository, ReportRepository reportRepository) {
        this.restContractCheckRepository = restContractCheckRepository;
        this.reportRepository = reportRepository;
    }

    public TestReport runAndGetSavedReportWithId(String name, String url) throws MalformedURLException {

        System.out.println("Called URL: "+url);

        TestReport toSave = restContractCheckRepository.runCheck(name, new URL(url));
        // TODO: czy nie trzeba zwalidować czy się zachował?
        reportRepository.addReportToRepository(toSave);

        return toSave;

    }



}
