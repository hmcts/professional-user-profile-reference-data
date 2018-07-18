package uk.gov.hmcts.reform.ref.pup.repository;

import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccountType;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PaymentAccountTypeRepository extends CrudRepository<PaymentAccountType, UUID> {

}
