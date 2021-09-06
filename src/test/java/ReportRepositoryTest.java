import com.sciamus.contractanalyzer.application.ReportFacade;
import com.sciamus.contractanalyzer.application.ReportFilterParameters;
import com.sciamus.contractanalyzer.application.ReportViewDTO;
import com.sciamus.contractanalyzer.infrastructure.port.ReportInfrastructureDTO;
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
import org.springframework.data.domain.Page;

import java.net.MalformedURLException;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class ReportRepositoryTest {


    @Mock
    private KeycloakSecurityContext keycloakSecurityContextMock;

    private ReportFacade reportFacade;


    ReportFilterParameters reportFilterParameters;

    @Mock
    private SecurityConfigurable securityConfigMock;

    @Mock
    private ReportPersistancePort reportPersistancePortMock;
    @BeforeEach
    public void init() {

        given(securityConfigMock.provideKeycloakSecurityContext()).willReturn(keycloakSecurityContextMock);
        given(reportPersistancePortMock.save(any())).willAnswer(AdditionalAnswers.returnsFirstArg());

        TestContextFactory testContextFactory = new TestContextFactory(securityConfigMock, reportPersistancePortMock);

        reportFacade = testContextFactory.getReportFacade();

         reportFilterParameters = new ReportFilterParameters(
                "PASSED", "BODY", LocalDateTime.MIN, LocalDateTime.MAX, "Dummy Check","test", "timestamp", "ASC"

        );



    }

    @Test
    @DisplayName("Facade <-> Port communication check")
    public void checkReportCreationTest() throws MalformedURLException {
        //given
        given(keycloakSecurityContextMock.getToken()).willReturn(new AccessToken());

        // when
        Page<ReportViewDTO> facadeFilteredReports = reportFacade.getFilteredReports(reportFilterParameters, 0);
        Page<ReportInfrastructureDTO> portFilteredReports = reportPersistancePortMock.findAll(reportFilterParameters, 0);


        // then
        assertThat(facadeFilteredReports).isEqualTo(portFilteredReports);
    }










}
