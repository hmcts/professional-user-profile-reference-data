package uk.gov.hmcts.reform.ref.pup.service;

import uk.gov.hmcts.reform.ref.pup.domain.Address;
import uk.gov.hmcts.reform.ref.pup.dto.AddressCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;

import java.util.UUID;

public interface AddressService {

    Address create(UUID organisationUuid, AddressCreation address) throws ApplicationException;
}