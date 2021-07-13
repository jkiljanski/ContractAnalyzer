package config;

import com.sciamus.contractanalyzer.AppConfig;
import com.sciamus.contractanalyzer.application.AggregatedChecksFacade;
import com.sciamus.contractanalyzer.application.ChecksFacade;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportConfig;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportsConfig;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.RestChecksConfig;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.checks.suites.CheckSuite;
import com.sciamus.contractanalyzer.domain.checks.suites.SuitesConfig;
import com.sciamus.contractanalyzer.domain.checks.suites.SuitesService;
import com.sciamus.contractanalyzer.infrastructure.adapter.RepositoryConfigurable;
import com.sciamus.contractanalyzer.misc.conf.SecurityConfigurable;

import java.util.List;

public class TestContextFactory {

    private final SecurityConfigurable securityConfigurable;

    public TestContextFactory(SecurityConfigurable securityConfigurable, RepositoryConfigurable repositoryConfigurable) {
        this.securityConfigurable = securityConfigurable;
        this.repositoryConfigurable = repositoryConfigurable;
    }

    private final RepositoryConfigurable repositoryConfigurable;


    ReportsConfig reportsConfig = new ReportsConfig();
    public RestChecksConfig restChecksConfig = new RestChecksConfig();
    AggregatedReportConfig aggregatedReportConfig = new AggregatedReportConfig();
    SuitesConfig suitesConfig = new SuitesConfig();


    public AppConfig getAppConfig() {

        //może to jeszcze gdzieś wyekspediować
        return new AppConfig(securityConfigurable);
    }


    // listę zapodać do RestChecksConfig
    private RestCheckRepository getRestCheckRepository(List<RestCheck> restCheckList) {
        return restChecksConfig.checkRepository(restCheckList, getCurrentUserService());
    }

    private CurrentUserService getCurrentUserService() {
        return getAppConfig().currentUserService();
    }

    private ReportService getReportsService() {
        return reportsConfig.reportService(repositoryConfigurable);
    }


    public ChecksFacade getChecksFacade(List<RestCheck> restCheckList) {

        return getAppConfig().contractChecksFacade(getRestCheckRepository(restCheckList), getReportsService());
    }

    public AggregatedChecksFacade getAggregatedChecksFacade(List<RestCheck> restCheckList) {

        return getAppConfig().aggregatedChecksFacade(aggregatedReportConfig.aggregatedChecksService(getCurrentUserService(), getRestCheckRepository(restCheckList), repositoryConfigurable.getAggregatedReportsRepository(), getReportsService()));


    }

    public SuitesService getSuitesService(List<CheckSuite> suites) {
        return  suitesConfig.suitesService(repositoryConfigurable.getSuitesReportsRepository(),suites);
    }


}
