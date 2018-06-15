package uk.gov.hmcts.reform.ref.pup.repository;

import org.springframework.data.repository.CrudRepository;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUserAccountAssignment;

import java.util.UUID;

public interface ProfessionalUserAccountAssignmentRepository extends CrudRepository<ProfessionalUserAccountAssignment,UUID> {
}
