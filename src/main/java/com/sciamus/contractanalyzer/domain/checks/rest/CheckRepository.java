package com.sciamus.contractanalyzer.domain.checks.rest;

import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.exception.CheckNotFoundException;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;



//TODO: semamantic inconsistecy: check repository / check service - maybe merge repository with service
@Repository
public class CheckRepository {

    private final List<RestContractCheck> restContractChecks;
    private final CurrentUserService currentUserService;


    public CheckRepository(List<RestContractCheck> restContractChecks, CurrentUserService currentUserService) {
        this.restContractChecks = restContractChecks;
        this.currentUserService = currentUserService;
    }

    public List<String> getAllChecks() {
        return restContractChecks.stream().map(RestContractCheck::getName).collect(Collectors.toList());
    }
//move to service


    public RestContractCheck findContractCheck(String name) {
        RestContractCheck restContractCheck = restContractChecks.stream()
                .filter(s->s.getName().equals(name))
                .findFirst().orElseThrow(()-> new CheckNotFoundException(name));
        return restContractCheck;
    }


}
