package com.sciamus.contractanalyzer.checks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Test not found")
public class CheckNotFoundException extends RuntimeException {


    public CheckNotFoundException(String testName) {
        super("Sorry, we're unable to find check named: " + testName);
    }
}
