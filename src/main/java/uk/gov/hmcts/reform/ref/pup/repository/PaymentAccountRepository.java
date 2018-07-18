package uk.gov.hmcts.reform.ref.pup.repository;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentAccountRepository extends CrudRepository<PaymentAccount, String> {

    List<PaymentAccount> findByPbaNumberAndOrganisation(String pbaNumber, Organisation organisation);

}
