package uk.gov.hmcts.reform.pup.repository;

import org.springframework.data.repository.CrudRepository;
import uk.gov.hmcts.reform.pup.domain.ProfessionalUser;

import java.util.UUID;

public interface ProfessionalUserRepository extends CrudRepository<ProfessionalUser,UUID> {
}
