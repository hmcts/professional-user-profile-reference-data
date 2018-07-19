package uk.gov.hmcts.reform.ref.pup.repository;

import uk.gov.hmcts.reform.ref.pup.domain.OrganisationType;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrganisationTypeRepository extends CrudRepository<OrganisationType, UUID> {

}
