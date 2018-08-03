package uk.gov.hmcts.reform.ref.pup.component.controller;

import uk.gov.hmcts.reform.auth.checker.spring.serviceanduser.ServiceAndUserDetails;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@EnableSpringDataWebSupport
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK)
public class RootControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();


        String firstTestOrganisationJson = "{\"name\":\"Solicitor Ltd\"}";

        MvcResult result = mvc.perform(post("/pup/organisations").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestOrganisationJson))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Organisation contentFromOrganisation = new ObjectMapper().readValue(contentAsString, Organisation.class);
        String organisationId = contentFromOrganisation.getUuid().toString();

        String firstTestPaymentAccountJson = "{\"pbaNumber\":\"pbaNumber1010\", \"organisationId\":\"" + organisationId + "\"}";


        mvc.perform(post("/pup/organisations/{uuid}/addresses", organisationId).with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"addressLine1\":\"address 1\"}"))
            .andDo(print());


        result = mvc.perform(post("/pup/payment-accounts").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestPaymentAccountJson))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        String firstTestUserJson = "{\"userId\":\"1\",\"firstName\":\"Alexis\",\"surname\":\"GAYTE\",\"email\":\"alexis.gayte@gmail.com\",\"phoneNumber\":\"+447591715204\"}";

        mvc.perform(post("/pup/professional-users").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestUserJson))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void myFullDetail_forAUserShouldReturnTheFullDetail() throws Exception {

        mvc.perform(get("/pup/mine").with(user(new ServiceAndUserDetails("1", "", Collections.emptyList(), "pui-webapp"))))
            .andExpect(status().isOk());
    }



}
