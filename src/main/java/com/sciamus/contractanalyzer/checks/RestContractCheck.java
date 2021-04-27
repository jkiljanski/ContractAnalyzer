package com.sciamus.contractanalyzer.checks;


import com.sciamus.contractanalyzer.reporting.checks.CheckReport;

import java.net.URL;

public interface RestContractCheck {


    CheckReport run(URL url);


    String getName();



}
