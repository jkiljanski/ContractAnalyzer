package com.sciamus.contractanalyzer.domain.checks.rest.reportcheck;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("unsecured")
@Service
public class CurrentUserServiceUnsecured implements CurrentUserService {


    @Override
    public String obtainUserName() {
        return "Run in unsecured profile";

    }
}