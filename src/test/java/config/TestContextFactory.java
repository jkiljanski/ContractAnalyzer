package config;

import com.sciamus.contractanalyzer.AppConfig;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportsConfig;
import com.sciamus.contractanalyzer.domain.checks.rest.RestChecksConfig;
import com.sciamus.contractanalyzer.misc.conf.SecurityConfigurable;

public class TestContextFactory {

    private final SecurityConfigurable securityConfigurable;

    public TestContextFactory(SecurityConfigurable securityConfigurable) {
        this.securityConfigurable = securityConfigurable;
    }

    ReportsConfig reportsConfig = new ReportsConfig();
    RestChecksConfig restChecksConfig = new RestChecksConfig();


    public AppConfig getAppConfig() {

        return new AppConfig(securityConfigurable);
    }

    public ReportsConfig getReportsConfig () {
        return  reportsConfig;
    }

    public RestChecksConfig getRestChecksConfig() {
        return restChecksConfig;
    }



}
