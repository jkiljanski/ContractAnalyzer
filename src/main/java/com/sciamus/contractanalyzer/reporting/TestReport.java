package com.sciamus.contractanalyzer.reporting;

import io.vavr.control.Option;

public abstract class TestReport {
    // change to ENUM
    private boolean result;
    //zbyt konkretnie -> na String
    private Option<String> report;

    public boolean getResult(){
        return result;
    }

    public Option<String> getReport() {
        return report;
    }
}
