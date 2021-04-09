package com.sciamus.contractanalyzer.control;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "GetListOfContractChecksCheckClient")
public interface GetListOfContractChecksCheckClient {

    @RequestMapping(method = RequestMethod.GET, value = "/restContractChecks")
    List<String> getListOfChecks();

}
