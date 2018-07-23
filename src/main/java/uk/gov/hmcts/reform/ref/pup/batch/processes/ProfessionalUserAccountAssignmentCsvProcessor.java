package uk.gov.hmcts.reform.ref.pup.batch.processes;

import uk.gov.hmcts.reform.ref.pup.batch.model.ProfessionalUserAccountAssignmentCsvModel;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationRequest;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountRequest;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserRequest;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.services.OrganisationService;
import uk.gov.hmcts.reform.ref.pup.services.PaymentAccountService;
import uk.gov.hmcts.reform.ref.pup.services.ProfessionalUserService;

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
       
        OrganisationRequest organisationRequest = new OrganisationRequest();
        organisationRequest.setName(orgName);
        Organisation organisation = organisationService.create(organisationRequest);
        
        ProfessionalUserRequest professionalUserRequest = new ProfessionalUserRequest();
        professionalUserRequest.setUserId(userEmail);
        professionalUserRequest.setEmail(userEmail);
        ProfessionalUser create = professionalUserService.create(professionalUserRequest);
        
        PaymentAccountRequest paymentAccountRequest = new PaymentAccountRequest();
        paymentAccountRequest.setOrganisationId(organisation.getUuid());
        paymentAccountRequest.setPbaNumber(pbaNumber);
        PaymentAccount paymentAccount = paymentAccountService.create(paymentAccountRequest);

        professionalUserService.assignPaymentAccount(professionalUserRequest.getUserId(), paymentAccount.getUuid());
        
        return create;
    }
}
