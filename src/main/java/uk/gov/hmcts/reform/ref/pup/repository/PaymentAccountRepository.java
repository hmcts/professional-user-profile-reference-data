package uk.gov.hmcts.reform.ref.pup.repository;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentAccountRepository extends CrudRepository<PaymentAccount, UUID> {

    Optional<PaymentAccount> findByPbaNumber(String pbaNumber);

    Optional<PaymentAccount> findByPbaNumberAndAndOrganisation(String pbaNumber, Organisation organisation);

    void deleteByPbaNumber(String pbaNumber);

}
