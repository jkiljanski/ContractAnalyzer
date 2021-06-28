package com.sciamus.contractanalyzer.domain.checks.rest;

import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserServiceSecured;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.exception.CheckNotFoundException;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CheckRepository {

    private final List<RestContractCheck> restContractChecks;
    private final CurrentUserServiceSecured currentUserServiceSecured;


    public CheckRepository(List<RestContractCheck> restContractChecks, CurrentUserServiceSecured currentUserServiceSecured) {
        this.restContractChecks = restContractChecks;
        this.currentUserServiceSecured = currentUserServiceSecured;
    }

    public List<String> getAllChecks() {
        return restContractChecks.stream().map(RestContractCheck::getName).collect(Collectors.toList());
    }

    public CheckReport runCheck (String name, URL url) {
        System.out.println("check was run");
        RestContractCheck restContractCheck = restContractChecks.stream()
                .filter(s->s.getName().equals(name))
                .findFirst().orElseThrow(()-> new CheckNotFoundException(name));
        return restContractCheck.run(url, new CheckReportBuilder().setUserName(currentUserServiceSecured.obtainUserName()));
    }


}
