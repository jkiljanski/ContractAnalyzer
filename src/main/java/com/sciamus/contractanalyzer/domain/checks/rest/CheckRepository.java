package com.sciamus.contractanalyzer.domain.checks.rest;

import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import com.sciamus.contractanalyzer.domain.exception.CheckNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CheckRepository {

    @Autowired
    private List<RestContractCheck> restContractChecks;

    @Autowired
    private CurrentUserService currentUserService;

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
