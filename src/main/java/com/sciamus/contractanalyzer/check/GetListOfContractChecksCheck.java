package com.sciamus.contractanalyzer.check;

import com.sciamus.contractanalyzer.control.GetListOfContractChecksCheckClient;
import com.sciamus.contractanalyzer.reporting.ReportResults;
import com.sciamus.contractanalyzer.reporting.TestReport;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.StringDecoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class GetListOfContractChecksCheck implements RestContractCheck {


    private final static String NAME = "List Contract Check Getting Check";

//    private final Client client;

//    @Autowired
//    public GetListOfContractChecksCheck(Client client) {
//        this.client = client;
//    }

    @Override
//TODO: pytanie: nie dość, że wymagamy od sprawdzanego serwera wystawienia konkrentego endpointa, to jeszcze definiujemy konkretą metodę??

    public TestReport run(URL url) {

        GetListOfContractChecksCheckClient testClient = Feign.builder()

//                .client(client)
                .target(GetListOfContractChecksCheckClient.class, url.toString());

        if (testClient.getListOfChecks().isEmpty()) {
            return new TestReport(ReportResults.FAILED, "Test failed");
        }
        System.out.println(testClient.getListOfChecks());
        return new TestReport(ReportResults.PASSED, "Test passed");
    }

    @Override
    public String getName() {
        return NAME;
    }
}
