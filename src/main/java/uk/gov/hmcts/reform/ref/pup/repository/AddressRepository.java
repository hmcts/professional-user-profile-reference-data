package uk.gov.hmcts.reform.ref.pup.repository;

import uk.gov.hmcts.reform.ref.pup.domain.Address;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AddressRepository extends CrudRepository<Address, UUID> {

}
