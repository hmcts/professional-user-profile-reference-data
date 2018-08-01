package uk.gov.hmcts.reform.ref.pup.service;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.dto.AddressCreation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;

import java.util.Optional;
import java.util.UUID;

public interface OrganisationService {

    Organisation findOrCreate(OrganisationCreation organisation) throws ApplicationException;

    Optional<Organisation> retrieve(UUID organisationUuid) throws ApplicationException;

    void delete(UUID organisationUuid) throws ApplicationException;

    Organisation addAddress(UUID organisationUuid, AddressCreation address) throws ApplicationException;

}
