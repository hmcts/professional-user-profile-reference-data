package uk.gov.hmcts.reform.ref.pup.repository;

import io.swagger.annotations.Api;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@PreAuthorize("hasRole('citizen')")
//@Api
public interface OrganisationRepository extends CrudRepository<Organisation,UUID> {
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.repository.CrudRepository#save(S)
//     */
//    @Override
//    @PreAuthorize("hasRole('solicitor')")
//    <S extends ProfessionalUser> S save(S s);
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.repository.CrudRepository#deleteById(java.lang.Object)
//     */
//    @Override
//    @PreAuthorize("hasRole('citzen')")
//    void deleteById(Long aLong);

    List<Organisation> findByName(String names);

}
