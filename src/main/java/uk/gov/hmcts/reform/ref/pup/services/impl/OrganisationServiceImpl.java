package uk.gov.hmcts.reform.ref.pup.services.impl;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.repository.OrganisationRepository;
import uk.gov.hmcts.reform.ref.pup.services.OrganisationService;

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
    public Organisation create(final Organisation organisation) throws ApplicationException {
        Optional<Organisation> organisationByName = organisationRepository.findOneByName(organisation.getName());
        if (organisationByName.isPresent()) {
            throw new ApplicationException("message");
        }
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
