package uk.gov.hmcts.reform.ref.pup.controller;

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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext
@EnableSpringDataWebSupport
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK)
public class RestDataControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;
    
    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void root_checkOutTheResultForTheRootPath() throws Exception {
        
        mvc.perform(get("/").with(user("user")))
                    .andExpect(status().isOk())
                    .andDo(print());
    }
    
    @Test
    public void paymentAccounts_checkOutTheResultForPaymentAccounts() throws Exception {
        
        mvc.perform(get("/paymentAccounts").with(user("user")))
                    .andExpect(status().isOk())
                    .andDo(print());
    }
    
    
    @Test
    public void profile_checkOutTheResultForProfile() throws Exception {
        
        mvc.perform(get("/profile").with(user("user")))
                    .andExpect(status().isOk())
                    .andDo(print());
    }
    
    @Test
    public void profile_organisations_checkOutTheResultForProfile() throws Exception {
        
        mvc.perform(get("/profile/organisations").with(user("user")))
                    .andExpect(status().isOk())
                    .andDo(print());
    }
}
