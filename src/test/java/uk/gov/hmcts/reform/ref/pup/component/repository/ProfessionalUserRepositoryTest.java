package uk.gov.hmcts.reform.ref.pup.component.repository;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.repository.OrganisationRepository;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProfessionalUserRepositoryTest {

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private ProfessionalUserRepository professionalUserRepository;

    private ProfessionalUser firstTestUser;
    private ProfessionalUser secondTestUser;

    @Before
    public void setUp() {
        Organisation organisation = createFakeOrganisation();
        organisationRepository.save(organisation);

        // first test user
        firstTestUser = new ProfessionalUser();
        firstTestUser.setEmail("alexis.gayte@gmail.com");
        firstTestUser.setFirstName("alexis");
        firstTestUser.setPhoneNumber("00711223");
        firstTestUser.setSurname("gayte");
        firstTestUser.setUserId("01");
        firstTestUser.setOrganisation(organisation);

        professionalUserRepository.save(firstTestUser);

        // second test user
        secondTestUser = new ProfessionalUser();
        secondTestUser.setEmail("alexis.gayte@hmcts.net");
        secondTestUser.setFirstName("alexis");
        secondTestUser.setPhoneNumber("00711223");
        secondTestUser.setSurname("gayte");
        secondTestUser.setUserId("02");
        secondTestUser.setOrganisation(organisation);

        professionalUserRepository.save(secondTestUser);

    }

    private Organisation createFakeOrganisation() {
        Organisation firstTestOrganisation = new Organisation();
        firstTestOrganisation.setName("AGA");
        return firstTestOrganisation;
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

    @After
    public void tearDown() {
        professionalUserRepository.delete(firstTestUser);
        professionalUserRepository.delete(secondTestUser);
    }

    @Test
    public void findOneByEmail_ShouldReturnNoPresentIfNoUserIsAssociatedWithEmail() throws Exception {
        Optional<ProfessionalUser> findByEmail = professionalUserRepository.findOneByEmail("");

        assertFalse(findByEmail.isPresent());
    }

    @Test
    public void findOneByEmail_ShouldReturnTheUserAssociatesWithEmail() throws Exception {
        Optional<ProfessionalUser> findByEmail = professionalUserRepository.findOneByEmail("alexis.gayte@hmcts.net");

        assertThat("alexis.gayte@hmcts.net", equalTo(findByEmail.get().getEmail()));
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void modelCheck_ShouldNotBeAbleToCreateToUserWithTheSameUserId() throws Exception {

        ProfessionalUser testUser = createFakeProfessionalUser();
        testUser.setUserId(firstTestUser.getUserId());
        testUser.setOrganisation(firstTestUser.getOrganisation());

        professionalUserRepository.save(testUser);

        fail();
    }

}
