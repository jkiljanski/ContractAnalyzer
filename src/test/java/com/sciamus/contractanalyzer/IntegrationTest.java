//package com.sciamus.contractanalyzer;
//
//import com.mongodb.BasicDBObjectBuilder;
//import com.mongodb.DBObject;
//import com.sciamus.contractanalyzer.application.ChecksFacade;
//import com.sciamus.contractanalyzer.domain.checks.reports.Report;
//import com.sciamus.contractanalyzer.domain.checks.reports.ReportService;
//import com.sciamus.contractanalyzer.misc.CurrentUserService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.BDDMockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.net.MalformedURLException;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//@AutoConfigureMockMvc
//@AutoConfigureDataMongo
//@SpringBootTest(classes = {GlobalConfig.class/*, MockedSecurityConfig.class*/})
///*@ContextConfiguration(loader= AnnotationConfigContextLoader.class,
//        classes = {GlobalConfig.class, MockedSecurityConfig.class})*/
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class IntegrationTest{
//
//    @Autowired
//    ChecksFacade checksFacade;
//
//    @Autowired
//    ReportService reportService;
//
//    @MockBean
//    CurrentUserService currentUserService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    /*@Autowired
//    private WebApplicationContext webApplicationContext;*/
//
//
//    /*@BeforeEach
//    public void setup() throws Exception {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
//    }*/
//
//
//    @DisplayName("given object to save"
//            + " when save object using MongoDB template"
//            + " then object is saved")
//    @Test
//    public void test1(@Autowired MongoTemplate mongoTemplate) {
//
//
//        // given
//        DBObject objectToSave = BasicDBObjectBuilder.start()
//                .add("key", "value")
//                .get();
//
//        // when
//        mongoTemplate.save(objectToSave, "collection");
//
//        // then
//        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
//                .containsOnly("value");
//    }
//
//    @DisplayName("single check is saved")
//    @Test
//    public void test2() throws MalformedURLException {
//
//        BDDMockito.willReturn("test user").given(currentUserService).obtainUserName();
//        // given
//        checksFacade.runAndGetSavedReportWithId("Dummy Check", "http://localhost:8080");
//
//        // when
//        List<Report> allReports = reportService.getAllReports();
//
//        // then
//        System.out.println(allReports);
//        assertThat(allReports).hasSize(1);
//
//    }
//
//    @DisplayName("rest calls save report")
//    @Test
//    public void test3() throws Exception {
//
//        BDDMockito.willReturn("test user").given(currentUserService).obtainUserName();
//        // given
//        mockMvc.perform(post("/checks/Dummy Check/run").param("url","http://localhost:8080")).andDo(print());
//        // when
//        List<Report> allReports = reportService.getAllReports();
//
//        // then
//        System.out.println(allReports);
//        assertThat(allReports).hasSize(1);
//
//    }
//
//
//
//}
