package uk.gov.hmcts.reform.ref.pup.services.domain;

import uk.gov.hmcts.reform.ref.pup.batch.domain.ProfessionalUserAccountAssignmentCsvDTO;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUserAccountAssignment;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserAccountAssignmentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionalUserAccountAssignmentService {

    private static final Logger log = LoggerFactory.getLogger(ProfessionalUserAccountAssignmentService.class);


    private ProfessionalUserAccountAssignmentRepository professionalUserAccountAssignmentRepository;

    private ProfessionalUserService professionalUserService;

    private PaymentAccountService paymentAccountService;

    @Autowired
    public ProfessionalUserAccountAssignmentService(ProfessionalUserAccountAssignmentRepository professionalUserAccountAssignmentRepository,
                                                    ProfessionalUserService professionalUserService,
                                                    PaymentAccountService paymentAccountService) {
        this.professionalUserAccountAssignmentRepository = professionalUserAccountAssignmentRepository;
        this.professionalUserService = professionalUserService;
        this.paymentAccountService = paymentAccountService;
    }



    public ProfessionalUserAccountAssignment createProfessionalUserAccountWithDTO(ProfessionalUserAccountAssignmentCsvDTO professionalUserAccountAssignmentCsvDTO) throws ApplicationException {
        final String orgName = professionalUserAccountAssignmentCsvDTO.getOrgName();
        final String pbaNumber = professionalUserAccountAssignmentCsvDTO.getPbaNumber();
        final String userEmail = professionalUserAccountAssignmentCsvDTO.getUserEmail();

        final PaymentAccount paymentAccount = paymentAccountService.createPaymentAccountWithPbaNumberAndOrgName(pbaNumber, orgName);
        final ProfessionalUser professionalUser = professionalUserService.createProfessionalUserWithEmail(userEmail);

        List<ProfessionalUserAccountAssignment> professionalUserAccountAssignmentList =
            professionalUserAccountAssignmentRepository.findAllByPaymentAccountAndUser(paymentAccount,professionalUser);

        ProfessionalUserAccountAssignment professionalUserAccountAssignment = new ProfessionalUserAccountAssignment();
        professionalUserAccountAssignment.setPaymentAccount(paymentAccount);
        professionalUserAccountAssignment.setUser(professionalUser);
                
        return professionalUserAccountAssignmentList.isEmpty() ? professionalUserAccountAssignmentRepository.save(professionalUserAccountAssignment)
            : professionalUserAccountAssignmentList.get(0);
    }

}
