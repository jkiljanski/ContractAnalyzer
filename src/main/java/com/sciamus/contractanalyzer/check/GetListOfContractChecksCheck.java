package com.sciamus.contractanalyzer;

import com.sciamus.contractanalyzer.check.RestContractCheck;
import com.sciamus.contractanalyzer.control.TestClient;
import com.sciamus.contractanalyzer.reporting.ReportResults;
import com.sciamus.contractanalyzer.reporting.TestReport;
import feign.Client;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.cloud.openfeign.FeignClientFactoryBean;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;


@Component
public class GetListOfContractChecksCheck implements RestContractCheck {


    private final static String NAME = "List Contract Check Getting Check";

    private final Client client;

    @Autowired
    public GetListOfContractChecksCheck(Client client) {
        this.client = client;
    }

    @Override
    public TestReport run(URL url) {

        TestClient testClient = Feign.builder()
                .client(client)
                .target(TestClient.class, url.toString());

        List<String> listOfChecks = testClient.getListOfChecks();


        TestReport report;
        if(!listOfChecks.isEmpty()) {
            report = new TestReport(ReportResults.PASSED, "");
        }
        else {
            report = new TestReport(ReportResults.FAILED,"");
        }
    return  report;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
