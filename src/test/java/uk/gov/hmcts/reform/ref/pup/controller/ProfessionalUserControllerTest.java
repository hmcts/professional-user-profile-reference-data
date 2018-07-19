package uk.gov.hmcts.reform.ref.pup.controller;

import uk.gov.hmcts.reform.auth.checker.spring.serviceanduser.AuthCheckerServiceAndUserFilter;
import uk.gov.hmcts.reform.ref.pup.componenttests.backdoors.ServiceResolverBackdoor;
import uk.gov.hmcts.reform.ref.pup.componenttests.backdoors.UserResolverBackdoor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@DirtiesContext
@EnableSpringDataWebSupport
@RunWith(SpringRunner.class)
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

    @Before
    public void setUp() {
      mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
      filter.setCheckForPrincipalChanges(true);
      filter.setInvalidateSessionOnPrincipalChange(true);
    }
    
    

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void professionalUserControllerTest() throws Exception {
        
    }
}

