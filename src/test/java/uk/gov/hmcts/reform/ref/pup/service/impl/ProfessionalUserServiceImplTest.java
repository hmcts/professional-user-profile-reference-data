package uk.gov.hmcts.reform.ref.pup.service.impl;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProfessionalUserServiceImplTest {

    @Mock
    private ProfessionalUserRepository professionalUserRepository;

    @InjectMocks
    private ProfessionalUserServiceImpl professionalUserService;

    @Captor
    ArgumentCaptor<ProfessionalUser> professionalUserCaptor;

    private ProfessionalUserCreation testUserRequest;
    private ProfessionalUser testUser;

    @Before
    public void setUp() {

        testUserRequest = createFakeProfessionalUserRequest();
        testUser = createFakeProfessionalUser();
    }

    private ProfessionalUser createFakeProfessionalUser() {
        ProfessionalUser firstTestUser = new ProfessionalUser();
        firstTestUser.setEmail("DUMMY@DUMMY.com");
        firstTestUser.setFirstName("DUMMY");
        firstTestUser.setPhoneNumber("DUMMY");
        firstTestUser.setSurname("DUMMY");
        firstTestUser.setUserId("DUMMY");
        return firstTestUser;
    }

    private ProfessionalUserCreation createFakeProfessionalUserRequest() {
        ProfessionalUserCreation firstTestUser = new ProfessionalUserCreation();
        firstTestUser.setEmail("DUMMY@DUMMY.com");
        firstTestUser.setFirstName("DUMMY");
        firstTestUser.setPhoneNumber("DUMMY");
        firstTestUser.setSurname("DUMMY");
        firstTestUser.setUserId("DUMMY");
        firstTestUser.setOrganisationId("c6c561cd-8f68-474e-89d3-13fece9b66f7");
        return firstTestUser;
    }


    private ProfessionalUser createFakeProfessionalUserWithSameEmail() {
        ProfessionalUser foolTestUser = new ProfessionalUser();
        foolTestUser.setEmail("DUMMY@DUMMY.com");
        foolTestUser.setFirstName("FOOL");
        foolTestUser.setPhoneNumber("FOOL");
        foolTestUser.setSurname("FOOL");
        foolTestUser.setUserId("FOOL");
        return foolTestUser;
    }

    @Test(expected = ApplicationException.class)
    public void createWithAnAlreadyUsedEmailShouldReturnAnException() throws ApplicationException {
        when(professionalUserRepository.findOneByEmail(testUser.getEmail())).thenReturn(Optional.of(createFakeProfessionalUserWithSameEmail()));
        when(professionalUserRepository.save(testUser)).thenReturn(testUser);

        professionalUserService.create(testUserRequest);
    }

    @Test
    public void retrieve() throws ApplicationException {
        when(professionalUserRepository.findOneByUserId(testUser.getUserId())).thenReturn(Optional.of(testUser));

        Optional<ProfessionalUser> retrieve = professionalUserService.retrieve(testUser.getUserId());

        assertThat(retrieve.get().getUserId(), equalTo(testUser.getUserId()));
    }

    @Test
    public void delete() throws ApplicationException {

        professionalUserService.delete(testUser.getUserId());

        verify(professionalUserRepository, only()).deleteByUserId(testUser.getUserId());
    }
}
