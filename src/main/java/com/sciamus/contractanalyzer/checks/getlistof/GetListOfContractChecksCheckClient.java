package com.sciamus.contractanalyzer.checks.getlistof;

import com.sciamus.contractanalyzer.control.FeignClientsConfig;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "GetListOfContractChecksCheckClient", configuration = FeignClientsConfig.class)
public interface GetListOfContractChecksCheckClient {


    @RequestLine(value = "GET /restContractChecks")
    ListOfChecksDTO getListOfChecks();


}
