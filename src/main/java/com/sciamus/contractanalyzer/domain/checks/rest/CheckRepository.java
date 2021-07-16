package com.sciamus.contractanalyzer.domain.checks.rest;

import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.exception.CheckNotFoundException;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.stream.Collectors;

public class CheckRepository {

    private final io.vavr.collection.List<RestContractCheck> restContractChecks;

    private final CurrentUserService currentUserService;

    @Autowired
    public CheckRepository(java.util.List<RestContractCheck> restContractChecks, CurrentUserService currentUserService) {
        this.restContractChecks = List.ofAll(restContractChecks);
        this.currentUserService = currentUserService;
    }

    public List<String> getAllChecks() {
        return List.ofAll(restContractChecks.toStream()
                .map(RestContractCheck::getName)
                .collect(Collectors.toList()));
    }

    public CheckReport runCheck (String name, URL url) {
        System.out.println("check was run");
        RestContractCheck restContractCheck = restContractChecks.toStream()
                .filter(s->s.getName().equals(name))
                .headOption().getOrElseThrow(()-> new CheckNotFoundException(name));
        return restContractCheck.run(url, new CheckReportBuilder().setUserName(currentUserService.obtainUserName()));
    }


}
