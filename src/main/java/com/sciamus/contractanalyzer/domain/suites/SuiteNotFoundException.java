package com.sciamus.contractanalyzer.domain.suites;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Suite not found")
public class SuiteNotFoundException extends RuntimeException {

    public SuiteNotFoundException(String suiteName) {

        super(suiteName);
    }
}
