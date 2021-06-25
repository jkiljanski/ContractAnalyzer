package com.sciamus.contractanalyzer.domain.checks.rest.reportcheck;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class CurrentUserService {

    private final KeycloakSecurityContext keycloakSecurityContext;

    @Autowired
    @Lazy
    public CurrentUserService(KeycloakSecurityContext keycloakSecurityContext) {
        this.keycloakSecurityContext = keycloakSecurityContext;
    }

    public String obtainUserName() {
        return keycloakSecurityContext.getToken().getPreferredUsername();
    }

}