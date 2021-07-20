package com.sciamus.contractanalyzer.misc.conf;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

public interface SecurityConfigurable {


    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    KeycloakSecurityContext provideKeycloakSecurityContext();

}
