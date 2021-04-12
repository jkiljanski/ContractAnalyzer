package com.sciamus.contractanalyzer.checks.getlistof;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "GetListOfContractChecksCheckClient")
public interface GetListOfContractChecksCheckClient {

    //    @GetMapping( "/restContractChecks")
    @RequestLine(value = "GET /restContractChecks")
    //TODO: czy tak jest okej - czy nie potrzeba bardziej skomplikowanego obiektu response???
    String getListOfChecks();

}
