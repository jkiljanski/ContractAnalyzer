package com.sciamus.contractanalyzer.check;

import com.sciamus.contractanalyzer.reporting.TestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.URL;
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

    public List<String> getAllChecks() {
        return restContractChecks.stream().map(RestContractCheck::getName).collect(Collectors.toList());
    }

    public TestReport runCheck (String name, URL url) {
        RestContractCheck r = restContractChecks.stream().filter(s->s.getName()==name).findFirst().orElse(null);
        TestReport t = r.run(url);
        return  t;
    }


}
