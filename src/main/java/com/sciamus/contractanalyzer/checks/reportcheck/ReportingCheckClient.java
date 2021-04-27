package com.sciamus.contractanalyzer.checks.reportcheck;


import com.sciamus.contractanalyzer.checks.getlistof.GetListOfContractChecksCheckResponseDTO;
import com.sciamus.contractanalyzer.control.CheckReportDTO;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.net.URL;

@FeignClient(value = "ReportingCheckClient")
public interface ReportingCheckClient {

    //czy nie lepiej skorzystać z dedykowanego klienta, żeby nie dublować kodu?

    @RequestLine(value = "GET /restContractChecks")
    GetListOfContractChecksCheckResponseDTO getAvailableChecks();

    @RequestLine(value = "GET /checks/{name}/autorun?url={url}")
    CheckReportDTO autogenerate(@Param("name") String name, @Param("url") URL url);

    @RequestLine(value = "GET /reports/{id}")
    CheckReportDTO getReportById(@Param("id") String id);

}


