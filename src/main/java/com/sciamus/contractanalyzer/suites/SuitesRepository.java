package com.sciamus.contractanalyzer.suites;

import com.sciamus.contractanalyzer.checks.RestContractCheckRepository;
import com.sciamus.contractanalyzer.reporting.suites.SuiteReport;
import com.sciamus.contractanalyzer.reporting.suites.SuiteReportMapper;
import com.sciamus.contractanalyzer.reporting.suites.SuitesReportsRepository;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.springframework.stereotype.Repository;

import java.net.URL;

@Repository
public class SuitesRepository {

    java.util.List<CheckSuite> checkSuites;
    RestContractCheckRepository restContractCheckRepository;
    SuitesReportsRepository suitesReportsRepository;
    SuiteReportMapper mapper;

    public SuitesRepository(java.util.List<CheckSuite> checkSuites,
                            RestContractCheckRepository restContractCheckRepository,
                            SuitesReportsRepository suitesReportsRepository,
                            SuiteReportMapper mapper) {
        this.checkSuites = checkSuites;
        this.restContractCheckRepository = restContractCheckRepository;
        this.suitesReportsRepository = suitesReportsRepository;
        this.mapper = mapper;
    }

    public java.util.List<String> getAllSuites() {

        return List.ofAll(checkSuites.stream())
                .map(CheckSuite::getName)
                .collect(List.collector())
                .toJavaList();
    }

    public SuiteReport runSuite(String name, String url) {

        List<CheckSuite> list;
        list = List.ofAll(checkSuites.stream());

        return list
                .filter(s -> s.getName().equals(name))
                .headOption()
                //nie można odpalić createURL, bo to side-effect - refactor needed
                .map(s -> s.run(createURL(url)))
                .getOrElseThrow(() -> new SuiteNotFoundException(name));
    }

    public SuiteReport runSuiteAndAddToRepository(String name, String url) {

        SuiteReport toSave = runSuite(name, url);

        return suitesReportsRepository.save(toSave);

    }

    public java.util.List<SuiteReport> getAllReports() {

        return suitesReportsRepository.findAll();

    }

    private URL createURL(String url) {
        return Try.of(() -> new URL(url)).getOrElseThrow(t -> new RuntimeException(t));
    }
}
