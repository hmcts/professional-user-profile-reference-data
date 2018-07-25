package uk.gov.hmcts.reform.ref.pup.component.controller;

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
public class OrganisationControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;
    
    private MockMvc mvc;
    
    private String firstTestAddressJson;

    private String organisationId;
    
    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        
        String firstTestOrganisationJson = "{\"name\":\"Solicitor Ltd\"}";
        firstTestAddressJson = "{\"addressLine1\":\"address 1\"}";
    
        MvcResult result = mvc.perform(post("/pup/organisation").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestOrganisationJson))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();
        
        
        String contentAsString = result.getResponse().getContentAsString();
        
        Organisation contentFrom = new ObjectMapper().readValue(contentAsString, Organisation.class);
        
        organisationId = contentFrom.getUuid().toString();
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void getOrganisation_forAOrganisationThatDoesnotExistShouldReturn404() throws Exception {
        
        mvc.perform(get("/pup/organisation/{uuid}", "c6c561cd-8f68-474e-89d3-13fece9b66f8").with(user("user")))
            .andExpect(status().isNotFound())
            .andDo(print());
    }
    
    @Test
    public void getOrganisation_forAOrganisationShouldReturnOrganisationDetail() throws Exception {
        
        mvc.perform(get("/pup/organisation/{uuid}", organisationId).with(user("user")))
            .andExpect(status().isOk())
            .andDo(print());
    }
    
    @Test
    public void deleteOrganisation_forAOrganisationShouldReturnNoContentAndTheUserShouldNotBeRequestable() throws Exception {
        
        mvc.perform(delete("/pup/organisation/{uuid}", organisationId).with(user("user")))
            .andExpect(status().isNoContent())
            .andDo(print());
        
        mvc.perform(get("/pup/organisation/{uuid}", organisationId).with(user("user")))
            .andExpect(status().isNotFound())
            .andDo(print());
    }
    
    @Test
    public void addOrganisationAddress_forAOrganisationShouldReturnOrganisationDetailWithTheAddress() throws Exception {
        
        mvc.perform(post("/pup/organisation/{uuid}/address", organisationId).with(user("user"))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(firstTestAddressJson))
            .andExpect(status().isOk())
            .andDo(print());
    }
    

}
