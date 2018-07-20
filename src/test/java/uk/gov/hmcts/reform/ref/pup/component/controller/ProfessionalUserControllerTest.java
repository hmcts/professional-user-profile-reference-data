package uk.gov.hmcts.reform.ref.pup.component.controller;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext
@EnableSpringDataWebSupport
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK)
public class ProfessionalUserControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;
    
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        
        // create a professional users
        String firstTestUserJson = "{\"userId\":\"1\",\"firstName\":\"Alexis\",\"surname\":\"GAYTE\",\"email\":\"alexis.gayte@gmail.com\",\"phoneNumber\":\"+447591715204\"}";
        
        mvc.perform(post("/pup/professionalUsers").with(user("user"))
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
    public void getProfessionalUser_forAUserThatDoesnotExistShouldReturn404() throws Exception {
        
        mvc.perform(get("/pup/professionalUsers/2").with(user("user")))
            .andExpect(status().isNotFound())
            .andDo(print());
    }
    
    @Test
    public void getProfessionalUser_forAUserShouldReturnUserDetail() throws Exception {
        
        mvc.perform(get("/pup/professionalUsers/1").with(user("user")))
            .andExpect(status().isOk())
            .andDo(print());
    }
}
