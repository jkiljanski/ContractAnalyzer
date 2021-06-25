package com.sciamus.contractanalyzer.domain.checks.rest;


import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;

import java.net.URL;

public interface RestContractCheck {


    CheckReport run(URL url, CheckReportBuilder builder);


    String getName();


}
