import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SuiteTest {

    /*@Mock
    private KeycloakSecurityContext keycloakSecurityContextMock;

    @Mock
    private SuitesReportsRepository suitesReportsRepositoryMock;

    @Mock
    private RepositoryConfigurable repositoryConfigurableMock;

    private SuitesService suitesService;

    @Mock
    private SecurityConfigurable securityConfigurableMock;

    @Mock
    ReportsRepository reportsRepositoryMock;

    @BeforeEach
    public void init() {

        given(securityConfigurableMock.provideKeycloakSecurityContext()).willReturn(keycloakSecurityContextMock);
        given(repositoryConfigurableMock.getSuitesReportsRepository()).willReturn(suitesReportsRepositoryMock);

        given(repositoryConfigurableMock.getReportsRepository()).willReturn(reportsRepositoryMock);

        TestContextFactory testContextFactory = new TestContextFactory(securityConfigurableMock, repositoryConfigurableMock);

        List<RestCheck> restChecks = new ArrayList<>();

        //kompletna aberracja - Basic Suite do wywalenia - ma w sobie pół systemu
        List<CheckSuite> checkSuites = List.of(new BasicSuite(testContextFactory.getChecksFacade(restChecks),
                testContextFactory.restChecksConfig.checkRepository(restChecks,testContextFactory.getAppConfig().currentUserService()),
                testContextFactory.getAppConfig().checkReportMapper()));

        suitesService = testContextFactory.getSuitesService(checkSuites);
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
        given(suitesReportsRepositoryMock.save(any())).willAnswer(AdditionalAnswers.returnsFirstArg());

        // when
        SuiteReport suiteReport = suitesService.runSuiteAndAddToRepository("Basic Suite", "http://localhost:8080");

        // then
        assertThat(suiteReport).isNotNull();
    }

    @Test
    @DisplayName("Suite report with bad URL crashed")
    public void suiteReportBadURLTest() {
        // when
        Exception exception = assertThrows(RuntimeException.class, () ->
                suitesService.runSuiteAndAddToRepository("Basic Suite", "httttp://localhost:8080"));

        // then
        assertThat(exception).hasMessageContaining("Bad URL!");
    }

    @Test
    @DisplayName("Suite report's unknown name crashed")
    public void suiteReportBadNameTest() {
        // when
        Exception exception = assertThrows(SuiteNotFoundException.class, () ->
            suitesService.runSuiteAndAddToRepository("Test Suite", "http://localhost:8080"));

        // then
        assertThat(exception).hasMessageContaining("Test Suite");
    }*/

}
