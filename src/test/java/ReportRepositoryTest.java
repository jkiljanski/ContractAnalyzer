import com.sciamus.contractanalyzer.application.ReportFacade;
import com.sciamus.contractanalyzer.application.ReportFilterParameters;
import com.sciamus.contractanalyzer.application.ReportViewDTO;
import com.sciamus.contractanalyzer.infrastructure.port.ReportInfrastructureDTO;
import com.sciamus.contractanalyzer.infrastructure.port.ReportPersistancePort;
import com.sciamus.contractanalyzer.misc.conf.SecurityConfigurable;
import config.TestContextFactory;
import config.TestReportInfrastructureDTO;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class ReportRepositoryTest {


    private ReportFacade reportFacade;


    ReportFilterParameters reportFilterParameters;

    @Mock
    private SecurityConfigurable securityConfigMock;

    @Mock
    private ReportPersistancePort reportPersistancePortMock;

    @BeforeEach
    public void init() {

        TestContextFactory testContextFactory = new TestContextFactory(securityConfigMock, reportPersistancePortMock);

        reportFacade = testContextFactory.getReportFacade();


        reportFilterParameters = new ReportFilterParameters(
                "PASSED", "BODY", LocalDateTime.MIN, LocalDateTime.MAX, "Dummy Check", "test", "timestamp", "ASC");


    }

    @Test
    @DisplayName("Report Facade <-> Report Port communication test")
    public void reportNotNullTest() {
        //given

        ReportInfrastructureDTO testReportInfrastructureDTO = new TestReportInfrastructureDTO();
        Page<ReportInfrastructureDTO> page = new PageImpl(List.of(testReportInfrastructureDTO), PageRequest.of(0, 10), 1);
        given(reportPersistancePortMock.findAll(reportFilterParameters, 0)).willReturn(page);

        // when
        Page<ReportViewDTO> facadeFilteredReports = reportFacade.getFilteredReports(reportFilterParameters, 0);

        // then
        assertThat(facadeFilteredReports).isNotNull();
    }

    @Test
    @DisplayName("report fields mapping test")
    public void reportObjectFieldsTest() {

        //given

        SoftAssertions softly = new SoftAssertions();

        ReportInfrastructureDTO testReportInfrastructureDTO = new TestReportInfrastructureDTO();
        Page<ReportInfrastructureDTO> page = new PageImpl(List.of(testReportInfrastructureDTO), PageRequest.of(0, 10), 1);
        given(reportPersistancePortMock.findAll(reportFilterParameters, 0)).willReturn(page);

        // when
        Page<ReportViewDTO> facadeFilteredReports = reportFacade.getFilteredReports(reportFilterParameters, 0);

        //then
        softly.assertThat(facadeFilteredReports.getTotalElements()).isEqualTo(1);
        ReportViewDTO reportViewDTO = facadeFilteredReports.get().findFirst().get();
        softly.assertThat(reportViewDTO.reportBody).isEqualTo(testReportInfrastructureDTO.reportBody);
        softly.assertThat(reportViewDTO.id).isEqualTo(testReportInfrastructureDTO.id);
        softly.assertThat(reportViewDTO.nameOfCheck).isEqualTo(testReportInfrastructureDTO.nameOfCheck);
        softly.assertThat(reportViewDTO.result).isEqualTo(testReportInfrastructureDTO.result);
        softly.assertThat(reportViewDTO.timestamp).isEqualTo(testReportInfrastructureDTO.timestamp);
        softly.assertThat(reportViewDTO.userName).isEqualTo(testReportInfrastructureDTO.userName);

        softly.assertAll();
    }


}
