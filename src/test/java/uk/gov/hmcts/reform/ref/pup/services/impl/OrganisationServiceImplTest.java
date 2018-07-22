package uk.gov.hmcts.reform.ref.pup.services.impl;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.OrganisationType;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.repository.OrganisationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.only;

@RunWith(SpringRunner.class)
public class OrganisationServiceImplTest {

    @Mock
    private OrganisationRepository organisationRepository;
    
    @InjectMocks
    private OrganisationServiceImpl organisationService;

    private Organisation testOrganisation;

    @Before
    public void setUp() {

        testOrganisation = createFakeOrganisation();
    }

    private Organisation createFakeOrganisation() {
        Organisation firstTestOrganisation = new Organisation();
        firstTestOrganisation.setName("DUMMY");
        firstTestOrganisation.setOrganisationType(new OrganisationType());
        firstTestOrganisation.setUuid(UUID.randomUUID());
        return firstTestOrganisation;
    }
    
    private Organisation createFakeOrganisationWithSamName() {
        Organisation foolTestOrganisation = new Organisation();
        foolTestOrganisation.setName("DUMMY");
        foolTestOrganisation.setOrganisationType(new OrganisationType());
        foolTestOrganisation.setUuid(UUID.randomUUID());
        return foolTestOrganisation;
    }

    @Test
    public void create() throws ApplicationException {
        Mockito.when(organisationRepository.save(testOrganisation)).thenReturn(testOrganisation);

        Organisation created = organisationService.create(testOrganisation);

        assertThat(testOrganisation, equalTo(created));
    }
    
    @Test(expected = ApplicationException.class)
    public void create_withAnAlreadyUsedEmailShouldReturnAnException() throws ApplicationException {
        Mockito.when(organisationRepository.findOneByName(testOrganisation.getName())).thenReturn(Optional.of(createFakeOrganisationWithSamName()));
        Mockito.when(organisationRepository.save(testOrganisation)).thenReturn(testOrganisation);
        
        organisationService.create(testOrganisation);
    }

    @Test
    public void retrieve() throws ApplicationException {
        Mockito.when(organisationRepository.findById(testOrganisation.getUuid())).thenReturn(Optional.of(testOrganisation));

        Optional<Organisation> retrieve = organisationService.retrieve(testOrganisation.getUuid());

        assertThat(retrieve.get().getUuid(), equalTo(testOrganisation.getUuid()));
    }
    

    @Test
    public void delete() throws ApplicationException {

        organisationService.delete(testOrganisation.getUuid());

        Mockito.verify(organisationRepository, only()).deleteById(testOrganisation.getUuid());
    }
    
}
