package uk.gov.hmcts.reform.ref.pup.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@PreAuthorize("hasRole('citizen')")
public interface ProfessionalUserRepository extends CrudRepository<ProfessionalUser,String> {

    List<ProfessionalUser> findByEmail(String s);

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
}
