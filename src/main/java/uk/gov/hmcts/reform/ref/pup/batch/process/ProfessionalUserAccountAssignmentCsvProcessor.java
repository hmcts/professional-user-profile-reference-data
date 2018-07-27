package uk.gov.hmcts.reform.ref.pup.batch.process;

import uk.gov.hmcts.reform.ref.pup.batch.model.ProfessionalUserAccountAssignmentCsvModel;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationCreation;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountCreation;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.service.OrganisationService;
import uk.gov.hmcts.reform.ref.pup.service.PaymentAccountService;
import uk.gov.hmcts.reform.ref.pup.service.ProfessionalUserService;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfessionalUserAccountAssignmentCsvProcessor implements ItemProcessor<ProfessionalUserAccountAssignmentCsvModel, ProfessionalUser> {

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private PaymentAccountService paymentAccountService;

    @Autowired
    private ProfessionalUserService professionalUserService;
    
    
    @Override
    public ProfessionalUser process(ProfessionalUserAccountAssignmentCsvModel professionalUserAccountAssignmentCsvModel) throws ApplicationException {

        final String orgName = professionalUserAccountAssignmentCsvModel.getOrgName();
        final String pbaNumber = professionalUserAccountAssignmentCsvModel.getPbaNumber();
        final String userEmail = professionalUserAccountAssignmentCsvModel.getUserEmail();
       
        OrganisationCreation organisationRequest = new OrganisationCreation();
        organisationRequest.setName(orgName);
        Organisation organisation = organisationService.create(organisationRequest);
        
        ProfessionalUserCreation professionalUserRequest = new ProfessionalUserCreation();
        professionalUserRequest.setUserId(userEmail);
        professionalUserRequest.setEmail(userEmail);
        ProfessionalUser create = professionalUserService.create(professionalUserRequest);
        
        PaymentAccountCreation paymentAccountRequest = new PaymentAccountCreation();
        paymentAccountRequest.setOrganisationId(organisation.getUuid());
        paymentAccountRequest.setPbaNumber(pbaNumber);
        PaymentAccount paymentAccount = paymentAccountService.create(paymentAccountRequest);

        professionalUserService.assignPaymentAccount(professionalUserRequest.getUserId(), paymentAccount.getUuid());
        
        return create;
    }
}
