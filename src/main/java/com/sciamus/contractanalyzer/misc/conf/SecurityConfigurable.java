package com.sciamus.contractanalyzer.misc.conf;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

public interface SecurityConfigurable {


    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    KeycloakSecurityContext provideKeycloakSecurityContext();



}
