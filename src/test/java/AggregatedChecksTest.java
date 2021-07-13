import com.sciamus.contractanalyzer.application.AggregatedChecksFacade;
import com.sciamus.contractanalyzer.domain.checks.aggregatedChecks.AggregatedReportDTO;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.dummy.DummyRestCheck;
import com.sciamus.contractanalyzer.infrastructure.adapter.RepositoryConfigurable;
import com.sciamus.contractanalyzer.infrastructure.port.AggregatedReportsRepository;
import com.sciamus.contractanalyzer.infrastructure.port.ReportsRepository;
import com.sciamus.contractanalyzer.misc.conf.SecurityConfigurable;
import config.TestContextFactory;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class AggregatedChecksTest {


    @Mock
    private KeycloakSecurityContext keycloakSecurityContextMock;

    @Mock
    private AggregatedReportsRepository aggregatedReportsRepositoryMock;

    @Mock
    private ReportsRepository reportsRepositoryMock;

    private AggregatedChecksFacade aggregatedChecksFacade;

    @Mock
    private SecurityConfigurable securityConfigMock;

    @Mock
    private RepositoryConfigurable repositoryConfigurableMock;


    @BeforeEach
    public void init() {

        given(securityConfigMock.provideKeycloakSecurityContext()).willReturn(keycloakSecurityContextMock);
        given(repositoryConfigurableMock.getAggregatedReportsRepository()).willReturn(aggregatedReportsRepositoryMock);
        given(repositoryConfigurableMock.getReportsRepository()).willReturn(reportsRepositoryMock);


        TestContextFactory testContextFactory = new TestContextFactory(securityConfigMock, repositoryConfigurableMock);

        List<RestCheck> restChecks = List.of(new DummyRestCheck());

        aggregatedChecksFacade = testContextFactory.getAggregatedChecksFacade(restChecks);

    }

    @Test
    @DisplayName("Creates report")
    public void aggregatedCheckReportCreationTest() throws MalformedURLException {
        //given

        given(keycloakSecurityContextMock.getToken()).willReturn(new AccessToken());
        given(reportsRepositoryMock.save(any())).willAnswer(AdditionalAnswers.returnsFirstArg());
        given(aggregatedReportsRepositoryMock.save(any())).willAnswer(AdditionalAnswers.returnsFirstArg());


        // when
        AggregatedReportDTO reportDTO = aggregatedChecksFacade.runAndSaveAggregatedChecks(null,List.of("Dummy Check"), "http://localhost:1212/dummyUrl");

        // then
        assertThat(reportDTO).isNotNull();
    }


}
