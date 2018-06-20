package uk.gov.hmcts.reform.ref.pup.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserRepository;
import uk.gov.hmcts.reform.ref.pup.services.domain.ProfessionalUserService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ProfessionalUserProfileServiceTest {

//    @Autowired
//    private ProfessionalUserRepository professionalUserRepository;
//
//    @Autowired
//    private ProfessionalUserService professionalUserProfileService;
//
//
//    private final static ProfessionalUser testUser = new ProfessionalUser();
//
//    @Test
//    public void createProfessionalUser() {
//        Mockito.when(professionalUserRepository.save(testUser))
//            .thenReturn(testUser);
//
//        ProfessionalUser created = professionalUserProfileService.createProfessionalUser(testUser);
//
//        assertEquals(created,testUser);
//    }
//
    @Test
    public void retrieveProfessionalUser() {
//        Mockito.when(professionalUserRepository.findById(testUser.getUserId()))
//            .thenReturn(Optional.of(testUser));
//
//        ProfessionalUser created = professionalUserProfileService.retrieveProfessionalUser(testUser.getUserId());
//
//        assertEquals(created.getUserId(),testUser.getUserId());
    }

}
