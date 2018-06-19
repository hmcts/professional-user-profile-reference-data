package uk.gov.hmcts.reform.ref.pup.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUserAccountAssignment;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUserAccountAssignmentCsvDTO;

import java.util.List;
import java.util.UUID;

public interface ProfessionalUserAccountAssignmentRepository extends CrudRepository<ProfessionalUserAccountAssignment,UUID> {

    List<ProfessionalUserAccountAssignment> findAllByPaymentAccountAndUser(PaymentAccount paymentAccount,
                                                                           ProfessionalUser user);
}
