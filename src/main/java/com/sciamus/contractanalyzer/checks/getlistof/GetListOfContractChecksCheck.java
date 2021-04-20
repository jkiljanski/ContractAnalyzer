package com.sciamus.contractanalyzer.checks.getlistof;

import com.sciamus.contractanalyzer.checks.RestContractCheck;
import com.sciamus.contractanalyzer.reporting.ReportResults;
import com.sciamus.contractanalyzer.reporting.TestReport;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class GetListOfContractChecksCheck implements RestContractCheck {


    private final static String NAME = "List Contract Check Getting Check";


    @Override
    public TestReport run(URL url) {

        GetListOfContractChecksCheckClient testClient = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GetListOfContractChecksCheckClient.class, url.toString());

        System.out.println(testClient.getListOfChecks());

        GetListOfContractChecksCheckResponseDTO responseDTO;

        responseDTO = testClient.getListOfChecks();

        if (responseDTO.listOfChecks.size() > 0 && responseDTO.listOfChecks.contains(GetListOfContractChecksCheck.NAME)) {


            return getPassedTestReport();
        }
        return getFailedTestReport();
    }


    //TODO: pomysł jak to wydelegować do oddzielnej klasy / ewentualnie zrobić TestReportBuilder
    private TestReport getFailedTestReport() {
        return new TestReport(ReportResults.FAILED, this.getName() + " FAILED", this.getName());
    }

    private TestReport getPassedTestReport() {
        return new TestReport(ReportResults.PASSED, this.getName() + " PASSED", this.getName());
    }

    @Override
    public String getName() {
        return NAME;
    }
}
