package com.sciamus.contractanalyzer.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

//agregacja wszystkich checków
@Repository
public class RestContractCheckRepository {
    private final List<RestContractCheck> restContractChecks;

    @Autowired
    public RestContractCheckRepository(List<RestContractCheck> restContractChecks) {
        this.restContractChecks = restContractChecks;
    }

    public List<String> getNames() {
        return restContractChecks.stream().map(RestContractCheck::getName).collect(Collectors.toList());
    }
}
