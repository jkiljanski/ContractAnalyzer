package com.sciamus.contractanalyzer.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestClientController {

    private final TestClient testClient;

    public TestClientController(TestClient testClient) {
        this.testClient = testClient;
    }

    @GetMapping("/test")
    public String checkIfChecksListNotEmpty (){
        if (!testClient.getListOfChecks().isEmpty()) {
            return "Everything is fine, available checks are: " + testClient.getListOfChecks();
        }
        return "Something went wrong, there are no checks to run";
    }

}
