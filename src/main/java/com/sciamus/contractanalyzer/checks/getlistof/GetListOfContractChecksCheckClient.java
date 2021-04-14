package com.sciamus.contractanalyzer.checks.getlistof;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "GetListOfContractChecksCheckClient")
public interface GetListOfContractChecksCheckClient {


    @RequestLine(value = "GET /restContractChecks")
    GetListOfContractChecksCheckResponseDTO getListOfChecks();




}
