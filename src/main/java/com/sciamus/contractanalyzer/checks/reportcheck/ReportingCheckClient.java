package com.sciamus.contractanalyzer.checks.reportcheck;


import com.sciamus.contractanalyzer.checks.getlistof.GetListOfContractChecksCheckResponseDTO;
import com.sciamus.contractanalyzer.control.TestReportDTO;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "ReportingCheckClient")
public interface ReportingCheckClient {


    //czy nie lepiej skorzystać z dedykowanego klienta, żeby nie dublować kodu?

    @RequestLine(value = "GET /restContractChecks")
    GetListOfContractChecksCheckResponseDTO getAvailableChecks();

    @RequestLine(value = "GET /checks/{name}/run")
    TestReportDTO runCheckAndGetReportWithId(@Param("name") String name);

    @RequestLine(value = "GET /reports/{id}")
    TestReportDTO getReportById(@Param("id") long id);

}


