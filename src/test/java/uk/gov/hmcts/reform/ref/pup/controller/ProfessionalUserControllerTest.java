package uk.gov.hmcts.reform.ref.pup.controller;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserRequest;
import uk.gov.hmcts.reform.ref.pup.services.ProfessionalUserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
public class ProfessionalUserControllerTest {

    @Mock
    protected ProfessionalUserService professionalUserService;
    
    @InjectMocks
    protected ProfessionalUserController professionalUserController;
    
    @Captor
    ArgumentCaptor<String> userIdCaptor;
    
    @Captor
    ArgumentCaptor<ProfessionalUserRequest> professionalUserCaptor;
    
    private MockMvc mvc;

    private ProfessionalUser firstTestUser;
    private String firstTestUserJson;
    
    @Before
    public void setUp() throws Exception {
        
        mvc = MockMvcBuilders.standaloneSetup(professionalUserController).build();
        
        firstTestUser = createFakeProfessionalUser();
        firstTestUserJson = "{\"userId\":\"1\",\"firstName\":\"DUMMY\",\"surname\":\"DUMMY\",\"email\":\"DUMMY@DUMMY.com\",\"phoneNumber\":\"DUMMY\"}";
        
    }

    private ProfessionalUser createFakeProfessionalUser() {
        ProfessionalUser firstTestUser = new ProfessionalUser();
        firstTestUser.setEmail("DUMMY@DUMMY.com");
        firstTestUser.setFirstName("DUMMY");
        firstTestUser.setPhoneNumber("DUMMY");
        firstTestUser.setSurname("DUMMY");
        firstTestUser.setUserId("1");
        return firstTestUser;
    }

    
    @Test
    public void createProfessionalUserShouldCallCreateFormProfessionalUserService() throws Exception {
        
        when(professionalUserService.create(any())).thenReturn(firstTestUser);
        
        mvc.perform(post("/pup/professionalUsers").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestUserJson))
            .andExpect(status().isOk())
            .andDo(print());
        
        verify(professionalUserService, only()).create(professionalUserCaptor.capture());
        assertThat(professionalUserCaptor.getValue().getUserId(), equalTo("1"));
        
    }
    
    @Test
    public void getProfessionalUserShouldReturnTheUser() throws Exception {

        when(professionalUserService.retrieve("1")).thenReturn(Optional.of(firstTestUser));

        mvc.perform(get("/pup/professionalUsers/1").with(user("user")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("userId", is("1")))
            .andExpect(jsonPath("email", is("DUMMY@DUMMY.com")))
            .andDo(print());
    }

    @Test
    public void getProfessionalUserShouldReturnNotFoundIfTheServiceReturnEmpty() throws Exception {
        
        when(professionalUserService.retrieve("1")).thenReturn(Optional.empty());   
        
        mvc.perform(get("/pup/professionalUsers/1").with(user("user")))
            .andExpect(status().isNotFound())
            .andDo(print());
    }
    
    @Test
    public void deleteProfessionalUserShouldCallTheDeleteFormProfessionalUserService() throws Exception {
        
        mvc.perform(delete("/pup/professionalUsers/1").with(user("user")))
            .andExpect(status().isNoContent())
            .andDo(print());
        
        verify(professionalUserService, only()).delete(userIdCaptor.capture());
        assertThat(userIdCaptor.getValue(), equalTo("1"));
        
    }
    
}
