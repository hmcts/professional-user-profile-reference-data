package uk.gov.hmcts.reform.ref.pup.it.repository;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.repository.OrganisationRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class OrganisationRepositoryTest {

    @Autowired
    private OrganisationRepository organisationRepository;

    private Organisation firstTestOrganisation;
    private Organisation secondTestOrganisation;

    @Before
    public void setUp() {

        // first test user
        firstTestOrganisation = new Organisation();
        firstTestOrganisation.setName("AGA");
       
        organisationRepository.save(firstTestOrganisation);
        
        // second test user
        secondTestOrganisation = new Organisation();
        secondTestOrganisation.setName("ABA");
       
        organisationRepository.save(secondTestOrganisation);
        
    }
    
    @After
    public void tearDown() {
        organisationRepository.delete(firstTestOrganisation);
        organisationRepository.delete(secondTestOrganisation);
    }

    @Test
    public void findOneByName_ShouldReturnNoPresentIfNoOrganisationIsAssociatedWithTheName() throws Exception {
        Optional<Organisation> findOneByName = organisationRepository.findOneByName("");
        
        assertFalse(findOneByName.isPresent());
    }
    
    @Test
    public void findOneByName_ShouldReturnTheOrganisationAssociatesWithTheName() throws Exception {
        Optional<Organisation> findOneByName = organisationRepository.findOneByName("ABA");
        
        assertThat("ABA", equalTo(findOneByName.get().getName()));
    }

}
