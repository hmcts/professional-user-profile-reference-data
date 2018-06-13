package uk.gov.hmcts.reform.ref.pup.controller;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.hmcts.reform.auth.checker.spring.serviceanduser.AuthCheckerServiceAndUserFilter;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.services.ProfessionalUserProfileService;
import uk.gov.hmcts.reform.ref.pup.test.util.backdoors.ServiceResolverBackdoor;
import uk.gov.hmcts.reform.ref.pup.test.util.backdoors.UserResolverBackdoor;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
//@WebMvcTest(ProfessionalUserController.class)
@SpringBootTest(webEnvironment = MOCK)
public class ProfessionalUserControllerTest {

    @Autowired
    protected ServiceResolverBackdoor serviceRequestAuthorizer;

    @Autowired
    protected UserResolverBackdoor userRequestAuthorizer;

    @Autowired
    protected AuthCheckerServiceAndUserFilter filter;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @MockBean
    private ProfessionalUserProfileService service;

    @Before
    public void setUp() {
        mvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        filter.setCheckForPrincipalChanges(true);
        filter.setInvalidateSessionOnPrincipalChange(true);
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {

        ProfessionalUser testUser = ProfessionalUser.builder().userId("ABC123").build();

        given(service.createProfessionalUser(testUser)).willReturn(testUser);

        mvc.perform(post("/pup/user")
            .header("Authorization",Collections.singletonList("testUser"))
            .header("ServiceAuthorization",Collections.singletonList("pui_webapp"))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}

