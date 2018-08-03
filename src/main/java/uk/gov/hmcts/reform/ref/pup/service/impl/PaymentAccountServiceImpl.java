package uk.gov.hmcts.reform.ref.pup.service.impl;

import uk.gov.hmcts.reform.ref.pup.domain.Address;
import uk.gov.hmcts.reform.ref.pup.domain.Assignment;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountAssignment;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException.ApplicationErrorCode;
import uk.gov.hmcts.reform.ref.pup.repository.AddressRepository;
import uk.gov.hmcts.reform.ref.pup.repository.AssignmentRepository;
import uk.gov.hmcts.reform.ref.pup.repository.PaymentAccountRepository;
import uk.gov.hmcts.reform.ref.pup.service.OrganisationService;
import uk.gov.hmcts.reform.ref.pup.service.PaymentAccountService;
import uk.gov.hmcts.reform.ref.pup.service.ProfessionalUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@Transactional
public class PaymentAccountServiceImpl implements PaymentAccountService {

    private final PaymentAccountRepository paymentAccountRepository;

    private final AssignmentRepository assignmentRepository;

    private final AddressRepository addressRepository;

    private final OrganisationService organisationService;

    private final ProfessionalUserService professionalUserService;

    @Autowired
    public PaymentAccountServiceImpl(PaymentAccountRepository paymentAccountRepository, AssignmentRepository assignmentRepository, AddressRepository addressRepository, OrganisationService organisationService, ProfessionalUserService professionalUserService) {
        this.paymentAccountRepository = paymentAccountRepository;
        this.organisationService = organisationService;
        this.professionalUserService = professionalUserService;
        this.assignmentRepository = assignmentRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public PaymentAccount create(PaymentAccountCreation paymentAccountInput) throws ApplicationException {

        Organisation organisation = organisationService.retrieve(paymentAccountInput.getOrganisationId())
                                                       .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.ORGANISATION_ID_DOES_NOT_EXIST));

        PaymentAccount paymentAccount = new PaymentAccount();

        paymentAccount.setPbaNumber(paymentAccountInput.getPbaNumber());
        paymentAccount.setPaymentAccountType(paymentAccountInput.getType());
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


        List<Assignment> assignments = assignmentRepository.findAllByProfessionalUser(professionalUser);

        return assignments.stream().map(Assignment::getPaymentAccount).collect(Collectors.toList());
    }

    @Override
    public void assign(String pbaNumber, PaymentAccountAssignment paymentAccountAssignment) throws ApplicationException {

        PaymentAccount paymentAccount = retrieve(pbaNumber)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.PAYMENT_ACCOUNT_ID_DOES_NOT_EXIST));

        ProfessionalUser professionalUser = professionalUserService.retrieve(paymentAccountAssignment.getUserId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.PROFESSIONAL_USER_ID_DOES_NOT_EXIST));

        Address address = addressRepository.findById(UUID.fromString(paymentAccountAssignment.getAddressId()))
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.ADDRESS_ID_DOES_NOT_EXIST));

        if (assignmentRepository.findOneByPaymentAccountAndAddressAndProfessionalUser(paymentAccount, address, professionalUser).isPresent()) {
            throw new ApplicationException(ApplicationErrorCode.PAYMENT_ACCOUNT_CAN_NOT_BE_ASSIGNED);
        }

        Assignment assignment = new Assignment();

        assignment.setAddress(address);
        assignment.setPaymentAccount(paymentAccount);
        assignment.setProfessionalUser(professionalUser);

        assignmentRepository.save(assignment);
    }

    @Override
    public void unassign(String pbaNumber, PaymentAccountAssignment paymentAccountAssignment) throws ApplicationException {

        PaymentAccount paymentAccount = retrieve(pbaNumber)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.PAYMENT_ACCOUNT_ID_DOES_NOT_EXIST));

        ProfessionalUser professionalUser = professionalUserService.retrieve(paymentAccountAssignment.getUserId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.PROFESSIONAL_USER_ID_DOES_NOT_EXIST));

        Address address = addressRepository.findById(UUID.fromString(paymentAccountAssignment.getAddressId()))
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.ADDRESS_ID_DOES_NOT_EXIST));

        Assignment assignment = assignmentRepository.findOneByPaymentAccountAndAddressAndProfessionalUser(paymentAccount, address, professionalUser)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.PAYMENT_ACCOUNT_CAN_NOT_BE_UNASSIGNED));


        assignmentRepository.delete(assignment);

    }
}
