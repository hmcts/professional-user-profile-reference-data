package uk.gov.hmcts.reform.ref.pup.service.impl;

import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException.ApplicationErrorCode;
import uk.gov.hmcts.reform.ref.pup.repository.PaymentAccountRepository;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserRepository;
import uk.gov.hmcts.reform.ref.pup.service.ProfessionalUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProfessionalUserServiceImpl implements ProfessionalUserService {

    private final ProfessionalUserRepository professionalUserRepository;

    private final PaymentAccountRepository paymentAccountRepository;

    @Autowired
    public ProfessionalUserServiceImpl(ProfessionalUserRepository professionalUserRepository, PaymentAccountRepository paymentAccountRepository) {
        this.professionalUserRepository = professionalUserRepository;
        this.paymentAccountRepository = paymentAccountRepository;
    }

    @Override
    public ProfessionalUser findOrCreate(final ProfessionalUserCreation professionalUserInput) throws ApplicationException {

        Optional<ProfessionalUser> professionalUsers = professionalUserRepository.findOneByEmail(professionalUserInput.getEmail());
        if (professionalUsers.isPresent()) {
            return professionalUsers.get();
        }

        ProfessionalUser professionalUser = new ProfessionalUser();
        professionalUser.setEmail(professionalUserInput.getEmail());
        professionalUser.setFirstName(professionalUserInput.getFirstName());
        professionalUser.setPhoneNumber(professionalUserInput.getPhoneNumber());
        professionalUser.setSurname(professionalUserInput.getSurname());
        professionalUser.setUserId(professionalUserInput.getUserId());

        return professionalUserRepository.save(professionalUser);
    }

    @Override
    public Optional<ProfessionalUser> retrieve(String userId) throws ApplicationException {
        return professionalUserRepository.findOneByUserId(userId);
    }

    @Override
    public void delete(String userId) throws ApplicationException {
        professionalUserRepository.deleteByUserId(userId);
    }

    @Override
    public void assignPaymentAccount(String userId, UUID paymentAccountId) throws ApplicationException {
        ProfessionalUser professionalUser = professionalUserRepository.findOneByUserId(userId)
                                                                      .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.PROFESSIONAL_USER_ID_DOES_NOT_EXIST));

        PaymentAccount paymentAccount = paymentAccountRepository.findById(paymentAccountId)
                                                                    .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.PAYMENT_ACCOUNT_ID_DOES_NOT_EXIST));


        professionalUser.getAccountAssignments().add(paymentAccount);

        professionalUserRepository.save(professionalUser);
    }

    @Override
    public void unassignPaymentAccount(String userId, UUID paymentAccountId) throws ApplicationException {
        ProfessionalUser professionalUser = professionalUserRepository.findOneByUserId(userId)
                                                                      .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.PROFESSIONAL_USER_ID_DOES_NOT_EXIST));

        PaymentAccount paymentAccount = paymentAccountRepository.findById(paymentAccountId)
                                                                    .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.PAYMENT_ACCOUNT_ID_DOES_NOT_EXIST));


        professionalUser.getAccountAssignments().remove(paymentAccount);

        professionalUserRepository.save(professionalUser);

    }
}
