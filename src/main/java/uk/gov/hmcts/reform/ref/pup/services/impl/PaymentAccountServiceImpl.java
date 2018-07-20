package uk.gov.hmcts.reform.ref.pup.services.impl;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.repository.PaymentAccountRepository;
import uk.gov.hmcts.reform.ref.pup.services.OrganisationService;
import uk.gov.hmcts.reform.ref.pup.services.PaymentAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
@Transactional
public class PaymentAccountServiceImpl implements PaymentAccountService {

    private final PaymentAccountRepository paymentAccountRepository;

    private final OrganisationService organisationService;

    @Autowired
    public PaymentAccountServiceImpl(PaymentAccountRepository paymentAccountRepository, OrganisationService organisationService) {
        this.paymentAccountRepository = paymentAccountRepository;
        this.organisationService = organisationService;
    }

    @Override
    public PaymentAccount create(String pbaNumber, UUID organisationId) throws ApplicationException {
        
        Organisation organisation = organisationService.retrieve(organisationId)
                                                       .orElseThrow(() -> new ApplicationException("meesage"));

        PaymentAccount paymentAccount = new PaymentAccount();
        
        paymentAccount.setPbaNumber(pbaNumber);
        paymentAccount.setOrganisation(organisation);
        
        return paymentAccountRepository.save(paymentAccount);
        
    }
    
    @Override
    public Optional<PaymentAccount> retrieve(String pbaNumber) {
        return paymentAccountRepository.findByPbaNumber(pbaNumber);
    }

    @Override
    public void delete(String pbaNumber) {
        paymentAccountRepository.deleteByPbaNumber(pbaNumber);
    }
}
