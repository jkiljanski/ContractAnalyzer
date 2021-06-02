package com.sciamus.contractanalyzer.checks;


import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.checks.CheckReportBuilder;

import java.net.URL;

public interface RestContractCheck {


    CheckReport run(URL url, CheckReportBuilder builder);


    String getName();


}
