package com.sciamus.contractanalyzer.domain.checks.rest.reportcheck;

public class CurrentUserServiceUnsecured implements CurrentUserService {

    public CurrentUserServiceUnsecured() {
    }

    @Override
    public String obtainUserName() {
        return "Run in unsecured profile";

    }
}