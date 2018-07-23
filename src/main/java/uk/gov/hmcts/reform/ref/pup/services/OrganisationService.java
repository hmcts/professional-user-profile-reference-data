package uk.gov.hmcts.reform.ref.pup.services;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;

import java.util.Optional;
import java.util.UUID;

public interface OrganisationService {

    Organisation create(OrganisationCreation organisation) throws ApplicationException;

    Optional<Organisation> retrieve(UUID uuid) throws ApplicationException;

    void delete(UUID uuid) throws ApplicationException;

}