import com.sciamus.contractanalyzer.application.ChecksFacade;
import com.sciamus.contractanalyzer.application.ReportViewDTO;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheck;
import com.sciamus.contractanalyzer.domain.checks.rest.dummy.DummyRestCheck;
import com.sciamus.contractanalyzer.infrastructure.port.ReportPersistancePort;
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
public class CheckTest {


    @Mock
    private KeycloakSecurityContext keycloakSecurityContextMock;

    private ChecksFacade checksFacade;

    @Mock
    private SecurityConfigurable securityConfigMock;

    @Mock
    private ReportPersistancePort reportPersistancePortMock;


    @BeforeEach
    public void init() {

        given(securityConfigMock.provideKeycloakSecurityContext()).willReturn(keycloakSecurityContextMock);
        given(reportPersistancePortMock.save(any())).willAnswer(AdditionalAnswers.returnsFirstArg());

        TestContextFactory testContextFactory = new TestContextFactory(securityConfigMock, reportPersistancePortMock);

        List<RestCheck> restChecks = List.of(new DummyRestCheck());

        checksFacade = testContextFactory.getChecksFacade(restChecks);

    }

    @Test
    @DisplayName("Creates report")
    public void checkReportCreationTest() throws MalformedURLException {
        //given
        given(keycloakSecurityContextMock.getToken()).willReturn(new AccessToken());

        // when
        ReportViewDTO reportViewDTO = checksFacade.runAndGetSavedReportWithId("Dummy Check", "http://localhost:1212/dummyUrl");

        // then
        assertThat(reportViewDTO).isNotNull();
    }
}
