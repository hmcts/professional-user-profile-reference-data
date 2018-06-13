package uk.gov.hmcts.reform.ref.pup.repository;

import org.springframework.data.repository.CrudRepository;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;

import java.util.UUID;

public interface ProfessionalUserRepository extends CrudRepository<ProfessionalUser,String> {
}
