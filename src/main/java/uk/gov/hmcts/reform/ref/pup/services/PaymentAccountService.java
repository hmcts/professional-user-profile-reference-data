package uk.gov.hmcts.reform.ref.pup.services;

import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountRequest;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;

import java.util.Optional;

public interface PaymentAccountService {

    PaymentAccount create(PaymentAccountRequest paymentAccount) throws ApplicationException;

    Optional<PaymentAccount> retrieve(String pbaNumber) throws ApplicationException;

    void delete(String pbaNumber) throws ApplicationException;

}