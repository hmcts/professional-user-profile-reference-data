package uk.gov.hmcts.reform.ref.pup.service;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;

import java.util.Optional;
import java.util.UUID;

public interface ProfessionalUserService {

    ProfessionalUser findOrCreate(ProfessionalUserCreation professionalUser) throws ApplicationException;

    Optional<ProfessionalUser> retrieve(String userId) throws ApplicationException;

    void delete(String userId) throws ApplicationException;

    void assignPaymentAccount(String userId, UUID paymentAccountId) throws ApplicationException;

    void unassignPaymentAccount(String userId, UUID paymentAccountId) throws ApplicationException;
}
