package com.sciamus.contractanalyzer.domain.checks.rest.reportcheck;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Profile("secured")
@Service
public class CurrentUserServiceSecured implements  CurrentUserService {

    private KeycloakSecurityContext keycloakSecurityContext;

    public String obtainUserName() {
        return keycloakSecurityContext.getToken().getPreferredUsername();
    }

    @Autowired
    @Lazy
    public CurrentUserServiceSecured(KeycloakSecurityContext keycloakSecurityContext) {
        this.keycloakSecurityContext = keycloakSecurityContext;
    }
}