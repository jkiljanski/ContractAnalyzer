package com.sciamus.contractanalyzer.domain.checks.rest.reportcheck;


import com.sciamus.contractanalyzer.domain.checks.rest.getlistof.ListOfChecksDTO;
import com.sciamus.contractanalyzer.application.ReportDTO;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.net.URL;

@FeignClient(value = "ReportingCheckClient")
public interface ReportingCheckClient {

    //czy nie lepiej skorzystać z dedykowanego klienta, żeby nie dublować kodu?

    @RequestLine(value = "GET /restContractChecks")
    ListOfChecksDTO getAvailableChecks();

    @RequestLine(value = "POST /checks/{name}/autorun?url={url}")
    ReportDTO autogenerate(@Param("name") String name, @Param("url") URL url);

    @RequestLine(value = "GET /reports/{id}")
    ReportDTO getReportById(@Param("id") String id);

}


