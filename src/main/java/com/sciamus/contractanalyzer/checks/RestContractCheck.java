package com.sciamus.contractanalyzer.checks;


import com.sciamus.contractanalyzer.reporting.TestReport;

import java.net.URL;

public interface RestContractCheck {


    TestReport run(URL url);


    String getName();



}
