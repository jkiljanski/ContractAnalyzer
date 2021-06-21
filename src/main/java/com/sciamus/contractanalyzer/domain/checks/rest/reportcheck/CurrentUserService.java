package com.sciamus.contractanalyzer.domain.checks.rest.reportcheck;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.sql.SQLOutput;

public class CurrentUserService {

    @Autowired
    @Lazy
    private KeycloakSecurityContext keycloakSecurityContext;

    public String obtainUserName() {
        return keycloakSecurityContext.getToken().getPreferredUsername();
    }

}