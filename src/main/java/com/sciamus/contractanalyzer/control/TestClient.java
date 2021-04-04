package com.sciamus.contractanalyzer.control;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "TestClient", url = "http://localhost:8080")
public interface TestClient {

    @RequestMapping(method = RequestMethod.GET, value = "/restContractCheck")
    List<String> getListOfChecks();

}
