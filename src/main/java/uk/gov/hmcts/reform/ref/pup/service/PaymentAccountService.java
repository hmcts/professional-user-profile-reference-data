package uk.gov.hmcts.reform.ref.pup.service;

import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;

import java.util.List;
import java.util.Optional;

public interface PaymentAccountService {

    PaymentAccount findOrCreate(PaymentAccountCreation paymentAccount) throws ApplicationException;

    Optional<PaymentAccount> retrieve(String pbaNumber) throws ApplicationException;

    void delete(String pbaNumber) throws ApplicationException;

    List<PaymentAccount> findByUserEmail(String email) throws ApplicationException;

}
