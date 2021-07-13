package config;

import com.sciamus.contractanalyzer.AppConfig;
import com.sciamus.contractanalyzer.application.AggregatedChecksFacade;
import com.sciamus.contractanalyzer.application.ChecksFacade;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportConfig;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportService;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportsConfig;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.RestChecksConfig;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
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
    RestChecksConfig restChecksConfig = new RestChecksConfig();
    AggregatedReportConfig aggregatedReportConfig = new AggregatedReportConfig();


    private AppConfig getAppConfig() {

        //może to jeszcze gdzieś wyekspediować
        return new AppConfig(securityConfigurable);
    }

    //to Refactor:
    private ReportsConfig getReportsConfig() {
        return reportsConfig;
    }

    private RestChecksConfig getRestChecksConfig() {
        return restChecksConfig;
    }




    // listę zapodać do RestChecksConfig
    public RestCheckRepository getRestCheckRepository(List<RestCheck> restCheckList) {
        return getRestChecksConfig().checkRepository(restCheckList, getCurrentUserService());
    }


    private CurrentUserService getCurrentUserService() {
        return getAppConfig().currentUserService();
    }

    private ReportService getReportsService() {
        return getReportsConfig().reportService(repositoryConfigurable);
    }


    public ChecksFacade getChecksFacade(List<RestCheck> restCheckList) {

        return getAppConfig().contractChecksFacade(getRestCheckRepository(restCheckList), getReportsService());
    }

    public AggregatedChecksFacade getAggregatedChecksFacade (List<RestCheck> restCheckList) {

       return getAppConfig().aggregatedChecksFacade(aggregatedReportConfig.aggregatedChecksService(getCurrentUserService(),getRestCheckRepository(restCheckList), repositoryConfigurable.getAggregatedReportsRepository(), getReportsService()));
    }


}
