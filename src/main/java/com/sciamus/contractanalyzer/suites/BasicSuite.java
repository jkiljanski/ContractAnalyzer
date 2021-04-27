package com.sciamus.contractanalyzer.suites;

import com.sciamus.contractanalyzer.checks.RestContractCheckRepository;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.suites.SuiteReport;
import com.sciamus.contractanalyzer.reporting.suites.SuiteReportMapper;
import io.vavr.collection.List;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.stream.Collectors;

@Component
public class BasicSuite extends CheckSuite {

    private final String NAME = "Basic Suite";

    private final String DESCRIPTION = "Runs 3 first checks of the system";

    RestContractCheckRepository restContractCheckRepository;

    SuiteReportMapper mapper;


    public BasicSuite(RestContractCheckRepository restContractCheckRepository, SuiteReportMapper mapper) {
        super();
        this.restContractCheckRepository = restContractCheckRepository;
        this.mapper = mapper;
    }


    public String getName() {
        return NAME;
    }

    @Override
    public SuiteReport run(URL url) {


        System.out.println("IMHERE" + restContractCheckRepository.getAllChecks());

        List<CheckReport> checkReports = List.ofAll(restContractCheckRepository.getAllChecks().stream())
                .take(3)
                .map(s -> restContractCheckRepository.runCheck(s, url))
                .peek(System.out::println)
                .collect(List.collector());


        SuiteReport s = new SuiteReport(checkReports.toJavaList());



        return s;
    }
}
