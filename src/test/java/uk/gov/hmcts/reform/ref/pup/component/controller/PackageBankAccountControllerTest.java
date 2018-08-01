package uk.gov.hmcts.reform.ref.pup.component.controller;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@EnableSpringDataWebSupport
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK)
public class PackageBankAccountControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    private String pbaNUmber;

    private String firstTestAssignmentJson;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        String firstTestOrganisationJson = "{\"name\":\"Solicitor Ltd\"}";

        MvcResult result = mvc.perform(post("/pup/organisation").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestOrganisationJson))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Organisation contentFromOrganisation = new ObjectMapper().readValue(contentAsString, Organisation.class);
        String organisationId = contentFromOrganisation.getUuid().toString();

        String firstTestPaymentAccountJson = "{\"pbaNumber\":\"pbaNumber1010\", \"organisationId\":\"" + organisationId + "\"}";
        pbaNUmber = "pbaNumber1010";

        result = mvc.perform(post("/pup/pba").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestPaymentAccountJson))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        String firstTestUserJson = "{\"userId\":\"1\",\"firstName\":\"Alexis\",\"surname\":\"GAYTE\",\"email\":\"alexis.gayte@gmail.com\",\"phoneNumber\":\"+447591715204\"}";

        mvc.perform(post("/pup/professionalUsers").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestUserJson))
            .andExpect(status().isOk())
            .andDo(print());

        firstTestAssignmentJson = "{\"userId\":\"1\"}";

    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void getPaymentAccount_forAPaymentAccountThatDoesnotExistShouldReturn404() throws Exception {

        mvc.perform(get("/pup/pba/{uuid}", "c6c561cd-8f68-474e-89d3-13fece9b66f8").with(user("user")))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    public void getPaymentAccount_forAPaymentAccountShouldReturnPaymentAccountDetail() throws Exception {

        mvc.perform(get("/pup/pba/{uuid}", pbaNUmber).with(user("user")))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void deletePaymentAccount_forAPaymentAccountShouldReturnNoContentAndTheUserShouldNotBeRequestable() throws Exception {

        mvc.perform(delete("/pup/pba/{uuid}", pbaNUmber).with(user("user")))
            .andExpect(status().isNoContent())
            .andDo(print());

        mvc.perform(get("/pup/pba/{uuid}", pbaNUmber).with(user("user")))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    public void assignPaymentAccounts_forPbaShouldReturnPaymentAccountDetail() throws Exception {

        mvc.perform(post("/pup/pba/{uuid}/assign", pbaNUmber).with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestAssignmentJson))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void assignPaymentAccounts_twiceForPbaShouldReturnAnError() throws Exception {

        mvc.perform(post("/pup/pba/{uuid}/assign", pbaNUmber).with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestAssignmentJson))
            .andExpect(status().isOk());

        mvc.perform(post("/pup/pba/{uuid}/assign", pbaNUmber).with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestAssignmentJson))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void unassignPaymentAccounts_forAPaymentAccountAssignedShouldReturnPaymentAccountDetail() throws Exception {

        mvc.perform(post("/pup/pba/{uuid}/assign", pbaNUmber).with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestAssignmentJson))
            .andExpect(status().isOk());

        mvc.perform(post("/pup/pba/{uuid}/unassign", pbaNUmber).with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestAssignmentJson))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void unassignPaymentAccounts_forAPaymentAccountAssignedShouldReturnError() throws Exception {

        mvc.perform(post("/pup/pba/{uuid}/unassign", pbaNUmber).with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestAssignmentJson))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Ignore
    @Test
    public void myPaymentAccounts_shouldReturnPaymentAccountDetailAssignedToMe() throws Exception {

        mvc.perform(post("/pup/pba/{uuid}/assign", pbaNUmber).with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestAssignmentJson))
            .andExpect(status().isOk());

        mvc.perform(get("/pup/pba/mine", pbaNUmber).with(user("1")))
            .andExpect(status().isOk())
            .andDo(print());
    }

}
