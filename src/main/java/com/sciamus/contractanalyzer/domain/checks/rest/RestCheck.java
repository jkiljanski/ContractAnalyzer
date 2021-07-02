package com.sciamus.contractanalyzer.domain.checks.rest;


import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportBuilder;

import java.net.URL;

public interface RestCheck {


    Report run(URL url, ReportBuilder builder);


    String getName();


}
