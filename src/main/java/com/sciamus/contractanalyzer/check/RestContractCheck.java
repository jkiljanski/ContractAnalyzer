package com.sciamus.contractanalyzer.check;


import com.sciamus.contractanalyzer.reporting.TestReport;

import java.net.URL;

interface RestContractCheck {

//    TestReport run(URL url);

    TestReport run(URL url);


    String getName();
}
