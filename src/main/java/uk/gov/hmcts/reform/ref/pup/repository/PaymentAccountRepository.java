package uk.gov.hmcts.reform.ref.pup.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;

import java.util.List;

//@PreAuthorize("hasRole('citizen')")
public interface PaymentAccountRepository extends CrudRepository<PaymentAccount,String> {
    List<PaymentAccount> findByPbaNumberAndOrganisation(String pbaNumber, Organisation organisation);
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
