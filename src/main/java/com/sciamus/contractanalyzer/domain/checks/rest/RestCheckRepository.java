package com.sciamus.contractanalyzer.domain.checks.rest;

import com.sciamus.contractanalyzer.domain.checks.exception.CheckNotFoundException;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class RestCheckRepository {

    private final List<RestCheck> restChecks;
    private final CurrentUserService currentUserService;


    public RestCheckRepository(List<RestCheck> restChecks, CurrentUserService currentUserService) {
        this.restChecks = restChecks;
        this.currentUserService = currentUserService;
    }

    public List<String> getAllChecks() {
        return restChecks.stream().map(RestCheck::getName).collect(Collectors.toList());
    }

    public Report runCheck(String name, URL url) {
        System.out.println("check was run");
        RestCheck restCheck = restChecks.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst().orElseThrow(() -> new CheckNotFoundException(name));
        return restCheck.run(url, new ReportBuilder().setUserName(currentUserService.obtainUserName()));
    }


}
