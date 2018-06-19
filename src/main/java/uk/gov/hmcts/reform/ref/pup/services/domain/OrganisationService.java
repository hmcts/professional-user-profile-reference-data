package uk.gov.hmcts.reform.ref.pup.services.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.OrganisationType;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.repository.OrganisationRepository;

import java.util.List;
import java.util.UUID;

@Service
public class OrganisationService {

    private OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public Organisation createOrganisationWithJustName(String name) {
        List<Organisation> organisations = organisationRepository.findByName(name);
        return organisations.isEmpty() ?
                createOrganisation(
                    Organisation.builder()
                    .name(name)
//                    .organisationType(OrganisationType.builder().name("UNKNOWN").build())
                    .build()
                )
            : organisations.get(0);
    }

    public Organisation createOrganisation(final Organisation organisation) {
        return organisationRepository.save(organisation);
    }

    public Organisation retrieveOrganisation(UUID uuid) {
        return organisationRepository.findById(uuid).orElse(null);
    }

    public void deleteProfessionalUser(UUID uuid) {
        organisationRepository.deleteById(uuid);
    }
}
