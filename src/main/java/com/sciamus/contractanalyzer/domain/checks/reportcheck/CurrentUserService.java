package com.sciamus.contractanalyzer.domain.checks.reportcheck;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private KeycloakSecurityContext keycloakSecurityContext;

    public String obtainUserName() {
        return keycloakSecurityContext.getToken().getPreferredUsername();
    }

    @Autowired
    @Lazy
    public CurrentUserService(KeycloakSecurityContext keycloakSecurityContext) {
        this.keycloakSecurityContext = keycloakSecurityContext;
    }
}