package uk.gov.hmcts.reform.ref.pup.service.impl;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException.ApplicationErrorCode;
import uk.gov.hmcts.reform.ref.pup.repository.OrganisationRepository;
import uk.gov.hmcts.reform.ref.pup.service.OrganisationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrganisationServiceImpl implements OrganisationService {

    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationServiceImpl(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    @Override
    public Organisation create(final OrganisationCreation organisationInput) throws ApplicationException {
        Optional<Organisation> organisationByName = organisationRepository.findOneByName(organisationInput.getName());
        if (organisationByName.isPresent()) {
            throw new ApplicationException(ApplicationErrorCode.ORGANISATION_ID_IN_USE);
        }

        Organisation organisation = new Organisation();
        organisation.setName(organisationInput.getName());
        organisation.setOrganisationType(organisationInput.getType());

        return organisationRepository.save(organisation);
    }

    @Override
    public Optional<Organisation> retrieve(UUID uuid) throws ApplicationException {
        return organisationRepository.findById(uuid);
    }

    @Override
    public void delete(UUID uuid) throws ApplicationException {
        organisationRepository.deleteById(uuid);
    }

}
