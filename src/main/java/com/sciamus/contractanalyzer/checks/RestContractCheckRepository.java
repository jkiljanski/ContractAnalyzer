package com.sciamus.contractanalyzer.checks;

import com.sciamus.contractanalyzer.checks.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.CheckReportBuilder;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RestContractCheckRepository {

    private final List<RestContractCheck> restContractChecks;
    private final CurrentUserService currentUserService;


    public RestContractCheckRepository(List<RestContractCheck> restContractChecks, CurrentUserService currentUserService) {
        this.restContractChecks = restContractChecks;
        this.currentUserService = currentUserService;
    }

    public List<String> getAllChecks() {
        return restContractChecks.stream().map(RestContractCheck::getName).collect(Collectors.toList());
    }

    public CheckReport runCheck (String name, URL url) {
        System.out.println("check was run");
        RestContractCheck restContractCheck = restContractChecks.stream()
                .filter(s->s.getName().equals(name))
                .findFirst().orElseThrow(()-> new CheckNotFoundException(name));
        return restContractCheck.run(url, new CheckReportBuilder().setUserName(currentUserService.obtainUserName()));
    }


}
