import com.sciamus.contractanalyzer.application.ContractChecksService;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.dummy.DummyRestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportRepository;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import com.sciamus.contractanalyzer.domain.reporting.idGenerator.ReportIdGenerator;
import com.sciamus.contractanalyzer.domain.reporting.suites.SuiteReport;
import com.sciamus.contractanalyzer.domain.reporting.suites.SuiteReportMapper;
import com.sciamus.contractanalyzer.domain.reporting.suites.SuitesReportsRepository;
import com.sciamus.contractanalyzer.domain.suites.BasicSuite;
import com.sciamus.contractanalyzer.domain.suites.SuiteNotFoundException;
import com.sciamus.contractanalyzer.domain.suites.SuitesRepository;
import com.sciamus.contractanalyzer.domain.suites.SuitesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@EnableMongoRepositories(basePackages = "com.sciamus.contractanalyzer.domain")
@ExtendWith(MockitoExtension.class)
public class SuiteTest {

    @Mock
    private KeycloakSecurityContext keycloakSecurityContextMock;

    @Mock
    private SuitesReportsRepository suitesReportsRepositoryMock;

    @Mock
    private ReportRepository reportRepositoryMock;

    private SuitesService suitesService;

    @BeforeEach
    public void init() {

//        final CurrentUserService currentUserService = new CurrentUserService();
//        final CheckRepository checkRepository = new CheckRepository();
//        CheckReportMapper checkReportMapper = new CheckReportMapper();
//        SuiteReportMapper suiteReportMapper = new SuiteReportMapper();
//        ReportIdGenerator reportIdGenerator = new ReportIdGenerator();
//        ReportService reportService = new ReportService();
//        ContractChecksService contractChecksService = new ContractChecksService();
//        SuitesRepository suitesRepository = new SuitesRepository();
        suitesService = new SuitesService();
    }

    @Test
    @DisplayName("URL from const string created")
    public void urlCreationTest() throws MalformedURLException {
        // when
        URL url = suitesService.createURL("http://localhost:8080/suites");

        // then
        assertThat(url).isNotNull();
    }

    @Test
    @DisplayName("Suite Report saved and returned")
    public void suiteReportTest() {
        //given
        given (keycloakSecurityContextMock.getToken()).willReturn(new AccessToken());
        given (suitesReportsRepositoryMock.save(any())).willAnswer(AdditionalAnswers.returnsFirstArg());

        // when
        SuiteReport suiteReport = suitesService.runSuiteAndAddToRepository("Basic Suite", "http://localhost:8080");

        // then
        assertThat(suiteReport).isNotNull();
    }

    @Test
    @DisplayName("Suite report with bad URL crashed")
    public void suiteReportBadURLTest() {
        // when
        Exception exception = assertThrows(RuntimeException.class, () -> {
            suitesService.runSuiteAndAddToRepository("Basic Suite", "httttp://localhost:8080");
        });

        // then
        assertThat(exception).hasMessageContaining("Bad URL!");
    }

    @Test
    @DisplayName("Suite report's unknown name crashed")
    public void suiteReportBadNameTest() {
        // when
        Exception exception = assertThrows(SuiteNotFoundException.class, () -> {
            suitesService.runSuiteAndAddToRepository("Test Suite", "http://localhost:8080");
        });

        // then
        assertThat(exception).hasMessageContaining("Test Suite");
    }

}
