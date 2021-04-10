package com.sciamus.contractanalyzer.control;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(value = "GetListOfContractChecksCheckClient")
public interface GetListOfContractChecksCheckClient {

//    @GetMapping( "/restContractChecks")
    @RequestLine(value="GET /restContractChecks")
    //TODO: czy tak jest okej - czy nie potrzeba bardziej skomplikowanego obiektu response???
    String getListOfChecks();

}
