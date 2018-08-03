package uk.gov.hmcts.reform.ref.pup.service.adaptor;

import uk.gov.hmcts.reform.ref.pup.converter.OrganisationConverter;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationCreation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationDto;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.service.OrganisationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrganisationServiceAdaptor {

    private final OrganisationService organisationService;
    private final OrganisationConverter organisationConverter;

    @Autowired
    public OrganisationServiceAdaptor(OrganisationService organisationService,
            OrganisationConverter organisationConverter) {
        this.organisationService = organisationService;
        this.organisationConverter = organisationConverter;
    }

    public OrganisationDto create(OrganisationCreation organisation) throws ApplicationException {
        return organisationConverter.apply(organisationService.create(organisation));
    }

    public Optional<OrganisationDto> retrieve(UUID organisationUuid) throws ApplicationException {
        Optional<Organisation> retrieve = organisationService.retrieve(organisationUuid);
        if (retrieve.isPresent()) {
            return Optional.of(organisationConverter.apply(retrieve.get()));
        } else {
            return Optional.empty();
        }
    }

    public void delete(UUID organisationUuid) throws ApplicationException {
        organisationService.delete(organisationUuid);
    }

}