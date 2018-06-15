package uk.gov.hmcts.reform.ref.pup.services.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.hmcts.reform.ref.pup.domain.*;
import uk.gov.hmcts.reform.ref.pup.repository.*;

public class ProfessionalUserAccountAssignmentCsvProcessor implements ItemProcessor<ProfessionalUserAccountAssignmentCsvDTO, ProfessionalUserAccountAssignment> {

    private static final Logger log = LoggerFactory.getLogger(ProfessionalUserAccountAssignmentCsvProcessor.class);

    @Autowired
    private ProfessionalUserAccountAssignmentRepository professionalUserAccountAssignmentRepository;

    @Autowired
    private ProfessionalUserRepository professionalUserRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private PaymentAccountRepository paymentAccountRepository;

    @Autowired
    private OrganisationTypeRepository organisationTypeRepository;

    @Autowired
    private PaymentAccountTypeRepository paymentAccountTypeRepository;

    @Override
    public ProfessionalUserAccountAssignment process(ProfessionalUserAccountAssignmentCsvDTO professionalUserAccountAssignmentCsvDTO) throws Exception {
        final String orgName = professionalUserAccountAssignmentCsvDTO.getOrgName();
        final String pbaNumber = professionalUserAccountAssignmentCsvDTO.getPbaNumber();
        final String userEmail = professionalUserAccountAssignmentCsvDTO.getUserEmail();


        final OrganisationType organisationType = OrganisationType
            .builder()
            .name("UNKNOWN")
            .build();

        final Organisation organisation =
            Organisation.builder()
                .name(orgName)
                .organisationType(organisationTypeRepository.save(organisationType))
                .build();

        final PaymentAccountType paymentAccountType = PaymentAccountType
            .builder()
            .name("UNKNOWN")
            .build();

        final PaymentAccount paymentAccount =
            PaymentAccount
                .builder()
                .pbaNumber(pbaNumber)
                .pbaType(paymentAccountTypeRepository.save(paymentAccountType))
                .organisation(organisationRepository.save(organisation))
                .build();

        final ProfessionalUser professionalUser = ProfessionalUser
            .builder()
            .userId(userEmail)
            .email(userEmail)
            .build();

        final ProfessionalUserAccountAssignment transformedPuaa = ProfessionalUserAccountAssignment
            .builder()
            .paymentAccount(paymentAccountRepository.save(paymentAccount))
            .user(professionalUserRepository.save(professionalUser))
            .build();

        log.info("Converting (" + professionalUserAccountAssignmentCsvDTO + ") into (" + transformedPuaa + ")\n\n\n\n");


        return professionalUserAccountAssignmentRepository.save(transformedPuaa);
    }
}
