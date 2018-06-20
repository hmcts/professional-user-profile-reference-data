package uk.gov.hmcts.reform.ref.pup.services.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ref.pup.domain.*;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserAccountAssignmentRepository;
import uk.gov.hmcts.reform.ref.pup.services.batch.ProfessionalUserAccountAssignmentCsvProcessor;

import java.util.List;
import java.util.UUID;

@Service
public class ProfessionalUserAccountAssignmentService {

    private static final Logger log = LoggerFactory.getLogger(ProfessionalUserAccountAssignmentCsvProcessor.class);


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



    public ProfessionalUserAccountAssignment createProfessionalUserAccountWithDTO(ProfessionalUserAccountAssignmentCsvDTO professionalUserAccountAssignmentCsvDTO) {
        final String orgName = professionalUserAccountAssignmentCsvDTO.getOrgName();
        final String pbaNumber = professionalUserAccountAssignmentCsvDTO.getPbaNumber();
        final String userEmail = professionalUserAccountAssignmentCsvDTO.getUserEmail();

        final PaymentAccount paymentAccount = paymentAccountService.createPaymentAccountWithPbaNumberAndOrgName(pbaNumber,orgName);
        final ProfessionalUser professionalUser = professionalUserService.createProfessionalUserWithEmail(userEmail);

        List<ProfessionalUserAccountAssignment> professionalUserAccountAssignmentList =
            professionalUserAccountAssignmentRepository.findAllByPaymentAccountAndUser(paymentAccount,professionalUser);

        return professionalUserAccountAssignmentList.isEmpty() ? professionalUserAccountAssignmentRepository.save(
            ProfessionalUserAccountAssignment
                .builder()
                .paymentAccount(paymentAccount)
                .user(professionalUser)
                .build()
        )
            : professionalUserAccountAssignmentList.get(0);

    }

//    public List<UUID> findDeperactedRowUUID(List<ProfessionalUserAccountAssignmentCsvDTO> professionalUserAccountAssignmentCsvDTOList) {
//        professionalUserAccountAssignmentRepository.findAllByPaymentAccount_PbaNumberAndAndUser_Email(professionalUserAccountAssignmentCsvDTOList);
//
//
//        return null;
//    }



}
