package com.sciamus.contractanalyzer.domain.checks.rest;

import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.exception.CheckNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class CheckRepository {

    private final List<RestContractCheck> restContractChecks;

    private final CurrentUserService currentUserService;

    @Autowired
    public CheckRepository(List<RestContractCheck> restContractChecks, CurrentUserService currentUserService) {
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
