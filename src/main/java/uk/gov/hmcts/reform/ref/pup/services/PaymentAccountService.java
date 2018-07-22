package uk.gov.hmcts.reform.ref.pup.services;

import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;

import java.util.Optional;
import java.util.UUID;

public interface PaymentAccountService {

    PaymentAccount create(String pbaNumber, UUID organisationId) throws ApplicationException;

    Optional<PaymentAccount> retrieve(String pbaNumber) throws ApplicationException;

    void delete(String pbaNumber) throws ApplicationException;

}