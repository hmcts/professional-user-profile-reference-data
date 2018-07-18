package uk.gov.hmcts.reform.ref.pup.repository;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfessionalUserRepository extends CrudRepository<ProfessionalUser, UUID> {

    Optional<ProfessionalUser> findOneByEmail(String email);

    Optional<ProfessionalUser> findOneByUserId(String userId);
    
    void deleteByUserId(String userId);
}
