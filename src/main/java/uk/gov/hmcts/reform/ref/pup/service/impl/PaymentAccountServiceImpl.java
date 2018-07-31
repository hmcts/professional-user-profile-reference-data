package uk.gov.hmcts.reform.ref.pup.service.impl;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException.ApplicationErrorCode;
import uk.gov.hmcts.reform.ref.pup.repository.PaymentAccountRepository;
import uk.gov.hmcts.reform.ref.pup.service.OrganisationService;
import uk.gov.hmcts.reform.ref.pup.service.PaymentAccountService;
import uk.gov.hmcts.reform.ref.pup.service.ProfessionalUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class PaymentAccountServiceImpl implements PaymentAccountService {

    private final PaymentAccountRepository paymentAccountRepository;

    private final OrganisationService organisationService;
    
    private final ProfessionalUserService professionalUserService;
    
    @Autowired
    public PaymentAccountServiceImpl(PaymentAccountRepository paymentAccountRepository, OrganisationService organisationService, ProfessionalUserService professionalUserService) {
        this.paymentAccountRepository = paymentAccountRepository;
        this.organisationService = organisationService;
        this.professionalUserService = professionalUserService;
    }

    @Override
    public PaymentAccount create(PaymentAccountCreation paymentAccountInput) throws ApplicationException {
        
        Organisation organisation = organisationService.retrieve(paymentAccountInput.getOrganisationId())
                                                       .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.PAYMENT_ACCOUNT_ID_DOES_NOT_EXIST));

        PaymentAccount paymentAccount = new PaymentAccount();
        
        paymentAccount.setPbaNumber(paymentAccountInput.getPbaNumber());
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
    
    @Override
    public List<PaymentAccount> retrieveForUser(String userId) throws ApplicationException {
        
        ProfessionalUser professionalUser = professionalUserService.retrieve(userId)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.PROFESSIONAL_USER_ID_DOES_NOT_EXIST));

        return new ArrayList<>(professionalUser.getAccountAssignments());
    }
}
