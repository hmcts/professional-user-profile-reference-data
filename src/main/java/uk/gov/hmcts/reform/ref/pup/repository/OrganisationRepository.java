package uk.gov.hmcts.reform.ref.pup.repository;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganisationRepository extends CrudRepository<Organisation, UUID> {

    Optional<Organisation> findOneByName(String names);

}
