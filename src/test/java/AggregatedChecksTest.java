import com.sciamus.contractanalyzer.application.ContractChecksService;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.dummy.DummyRestContractCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.reportcheck.CurrentUserService;
import com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks.AggregatedChecksReport;
import com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks.AggregatedChecksRepository;
import com.sciamus.contractanalyzer.domain.reporting.aggregatedChecks.AggregatedChecksService;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportRepository;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import com.sciamus.contractanalyzer.domain.reporting.idGenerator.AggregatedReportIdGenerator;
import com.sciamus.contractanalyzer.domain.reporting.idGenerator.ReportIdGenerator;
import io.vavr.collection.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AggregatedChecksTest  {


    @Mock
    private KeycloakSecurityContext keycloakSecurityContextMock;

    //        final CheckReportMapper checkReportMapper = new CheckReportMapper(currentUserService);



    @Mock
    private AggregatedChecksRepository aggregatedChecksRepositoryMock;

    @Mock
    private ReportRepository reportRepositoryMock;


    private AggregatedChecksService aggregatedChecksService;




    @BeforeEach
    public void init() {
        final CurrentUserService currentUserService = new CurrentUserService(keycloakSecurityContextMock);
        final CheckRepository checkRepository = new CheckRepository(Arrays.asList(new DummyRestContractCheck()), currentUserService);
        final ReportIdGenerator reportIdGenerator = new ReportIdGenerator(reportRepositoryMock);
        final ReportService reportService = new ReportService(reportRepositoryMock, reportIdGenerator);

        AggregatedReportIdGenerator aggregatedReportIdGenerator = new AggregatedReportIdGenerator(aggregatedChecksRepositoryMock);
        aggregatedChecksService = new AggregatedChecksService(checkRepository, aggregatedChecksRepositoryMock, aggregatedReportIdGenerator, currentUserService, reportService);
    }

    @Test
    @DisplayName("kot")
    public void aggregatedReportCreationTest() {
        //given

        AccessToken accessToken = new AccessToken();
        accessToken.setPreferredUsername("Test user");
        given(keycloakSecurityContextMock.getToken()).willReturn(accessToken);



        // when
        AggregatedChecksReport report = aggregatedChecksService.runAndSaveAggregatedChecks()

        // then



    }




}
