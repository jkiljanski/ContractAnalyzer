package config;

import com.sciamus.contractanalyzer.application.AggregatedChecksFacade;
import com.sciamus.contractanalyzer.application.ApplicationConfig;
import com.sciamus.contractanalyzer.application.ChecksFacade;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportConfig;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.RestChecksConfig;
import com.sciamus.contractanalyzer.domain.checks.suites.SuitesConfig;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportPersistancePort;
import com.sciamus.contractanalyzer.infrastructure.port.ReportPersistancePort;
import com.sciamus.contractanalyzer.misc.CurrentUserService;
import com.sciamus.contractanalyzer.misc.conf.SecurityConfigurable;

import java.util.List;

public class TestContextFactory {

    private final SecurityConfigurable securityConfigurable;
    private  ReportPersistancePort reportPersistencePort;
    private  AggregatedReportPersistancePort aggregatedReportPersistancePort;


    public TestContextFactory(SecurityConfigurable securityConfigurable, ReportPersistancePort reportPersistencePort) {
        this.securityConfigurable = securityConfigurable;
        this.reportPersistencePort = reportPersistencePort;
    }

    public TestContextFactory(SecurityConfigurable securityConfigurable, AggregatedReportPersistancePort aggregatedReportPersistancePort, ReportPersistancePort reportPersistancePort) {
        this.securityConfigurable = securityConfigurable;
        this.reportPersistencePort = reportPersistancePort;
        this.aggregatedReportPersistancePort = aggregatedReportPersistancePort;
    }



    public RestChecksConfig restChecksConfig = new RestChecksConfig();
    AggregatedReportConfig aggregatedReportConfig = new AggregatedReportConfig();
    SuitesConfig suitesConfig = new SuitesConfig();


    public ApplicationConfig getAppConfig() {

        //może to jeszcze gdzieś wyekspediować
        return new ApplicationConfig(securityConfigurable);
    }


    // listę zapodać do RestChecksConfig
    private RestCheckRepository getRestCheckRepository(List<RestCheck> restCheckList) {
        return restChecksConfig.checkRepository(restCheckList, getCurrentUserService());
    }

    private CurrentUserService getCurrentUserService() {
        return getAppConfig().currentUserService();
    }



    public ChecksFacade getChecksFacade(List<RestCheck> restCheckList) {

        return getAppConfig().checksFacade(getRestCheckRepository(restCheckList), reportPersistencePort);
    }

    public AggregatedChecksFacade getAggregatedChecksFacade(List<RestCheck> restCheckList) {

        return getAppConfig().aggregatedChecksFacade(getRestCheckRepository(restCheckList),aggregatedReportPersistancePort, getChecksFacade(restCheckList));

    }

//    public SuitesService getSuitesService(List<CheckSuite> suites) {
//        return suitesConfig.suitesService(persistanceConfigurable.getSuitesReportsRepository(), suites);
//    }


}
