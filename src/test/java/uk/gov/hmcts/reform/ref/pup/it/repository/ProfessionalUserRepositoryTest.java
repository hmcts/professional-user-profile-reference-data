package uk.gov.hmcts.reform.ref.pup.it.repository;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ProfessionalUserRepositoryTest {

    @Autowired
    private ProfessionalUserRepository professionalUserRepository;

    private ProfessionalUser firstTestUser;
    private ProfessionalUser secondTestUser;

    @Before
    public void setUp() {

        // first test user
        firstTestUser = new ProfessionalUser();
        firstTestUser.setEmail("alexis.gayte@gmail.com");
        firstTestUser.setFirstName("alexis");
        firstTestUser.setPhoneNumber("00711223");
        firstTestUser.setSurname("gayte");
       
        professionalUserRepository.save(firstTestUser);
        
        // second test user
        secondTestUser = new ProfessionalUser();
        secondTestUser.setEmail("alexis.gayte@hmcts.net");
        secondTestUser.setFirstName("alexis");
        secondTestUser.setPhoneNumber("00711223");
        secondTestUser.setSurname("gayte");
       
        professionalUserRepository.save(secondTestUser);
        
    }

    @After
    public void tearDown() {
        professionalUserRepository.delete(firstTestUser);
        professionalUserRepository.delete(secondTestUser);
    }

    @Test
    public void findByEmailShouldReturnNullIfNoUserWithEmailTest() throws Exception {
        Optional<ProfessionalUser> findByEmail = professionalUserRepository.findOneByEmail("");
        
        assertFalse(findByEmail.isPresent());
    }

}
