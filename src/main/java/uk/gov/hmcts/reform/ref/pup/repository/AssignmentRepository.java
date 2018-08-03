package uk.gov.hmcts.reform.ref.pup.repository;

import uk.gov.hmcts.reform.ref.pup.domain.Address;
import uk.gov.hmcts.reform.ref.pup.domain.Assignment;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssignmentRepository extends CrudRepository<Assignment, UUID> {

    List<Assignment> findAllByProfessionalUser(ProfessionalUser professionalUser);

    Optional<Assignment> findOneByPaymentAccountAndAddressAndProfessionalUser(PaymentAccount paymentAccount, Address address, ProfessionalUser professionalUser);

}