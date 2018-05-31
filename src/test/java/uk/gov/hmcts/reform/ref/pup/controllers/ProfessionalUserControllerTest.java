package uk.gov.hmcts.reform.ref.pup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.reform.pup.Application;
import uk.gov.hmcts.reform.pup.domain.ProfessionalUserProfile;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class)
@AutoConfigureMockMvc
public class ProfessionalUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_persist_a_session() throws Exception {

//        ProfessionalUserProfile session =
//                new ProfessionalUserProfile("My hearing");
//        String locationHeader = mvc.perform(post("/icp/sessions")
//                .content(objectMapper.writeValueAsString(session))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(header().string("location", containsString("/pup")))
//                .andReturn()
//                .getResponse()
//                .getHeader("location");
//
//        mvc.perform(get(locationHeader))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.loginId", equalTo(session.getLoginId())));
    }
}
