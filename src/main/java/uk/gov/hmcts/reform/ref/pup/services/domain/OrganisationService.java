package uk.gov.hmcts.reform.ref.pup.services.domain;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.repository.OrganisationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public Organisation createOrganisationWithJustName(String name) throws ApplicationException {

        Optional<Organisation> organisationByName = organisationRepository.findOneByName(name);
        if (organisationByName.isPresent()) {
            throw new ApplicationException("message");
        }

        Organisation organisation = new Organisation();
        organisation.setName(name);

        return createOrganisation(organisation);
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
