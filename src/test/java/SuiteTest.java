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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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

        final CurrentUserService currentUserService = new CurrentUserService(keycloakSecurityContextMock);
        final CheckRepository checkRepository = new CheckRepository(Arrays.asList(new DummyRestContractCheck()), currentUserService);
        CheckReportMapper checkReportMapper = new CheckReportMapper(currentUserService);
        SuiteReportMapper suiteReportMapper = new SuiteReportMapper(checkReportMapper);
        ReportIdGenerator reportIdGenerator = new ReportIdGenerator(reportRepositoryMock);
        ReportService reportService = new ReportService(reportRepositoryMock, reportIdGenerator);
        ContractChecksService contractChecksService = new ContractChecksService(checkRepository, reportService, checkReportMapper);
        SuitesRepository suitesRepository = new SuitesRepository(Arrays.asList(new BasicSuite(contractChecksService, checkReportMapper, checkRepository)));
        suitesService = new SuitesService(suitesReportsRepositoryMock, suiteReportMapper, suitesRepository);
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
