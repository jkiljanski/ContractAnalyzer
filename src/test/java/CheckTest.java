import com.sciamus.contractanalyzer.application.CheckReportDTO;
import com.sciamus.contractanalyzer.application.ContractChecksFacade;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportIdGenerator;
import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.dummy.DummyRestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.infrastructure.port.ReportRepository;
import config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.net.MalformedURLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class CheckTest {

    @Mock
    private KeycloakSecurityContext keycloakSecurityContextMock;

    @Mock
    private ReportRepository reportRepositoryMock;

    private ContractChecksFacade contractChecksFacade;

    @BeforeEach
    public void init() {


        final CurrentUserService currentUserService = new CurrentUserService(keycloakSecurityContextMock);
        final ReportIdGenerator reportIdGenerator = new ReportIdGenerator(reportRepositoryMock);
        final ReportService reportService = new ReportService(reportRepositoryMock, reportIdGenerator);

        final CheckReportMapper checkReportMapper = new CheckReportMapper(currentUserService);
        List<RestContractCheck> restContractChecks = List.of(new DummyRestContractCheck());
        final CheckRepository checkRepository = new CheckRepository(restContractChecks, currentUserService);
        contractChecksFacade = new ContractChecksFacade(checkRepository, reportService, checkReportMapper);
    }

    @Test
    @DisplayName("Creates report")
    public void checkReportCreationTest() throws MalformedURLException {
        //given
        given(keycloakSecurityContextMock.getToken()).willReturn(new AccessToken());

        // when
        CheckReportDTO checkReportDTO = contractChecksFacade.runAndGetSavedReportWithId("Dummy Check", "http://localhost:1212/dummyUrl");

        // then
        assertThat(checkReportDTO).isNotNull();
    }
}
