package com.sciamus.contractanalyzer;

import com.sciamus.contractanalyzer.misc.CurrentUserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

@Configuration
public class MockedSecurityConfig {

    @Primary
    @Scope("request")
    @Bean
    public CurrentUserService currentUserService() {
        return Mockito.mock(CurrentUserService.class);
    }

}
