package uk.gov.hmcts.reform.ref.pup.batch.services;

import uk.gov.hmcts.reform.ref.pup.batch.model.ProfessionalUserAccountAssignmentCsvModel;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
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
    public ProfessionalUser process(ProfessionalUserAccountAssignmentCsvModel professionalUserAccountAssignmentCsvModel) throws Exception {

        final String orgName = professionalUserAccountAssignmentCsvModel.getOrgName();
        final String pbaNumber = professionalUserAccountAssignmentCsvModel.getPbaNumber();
        final String userEmail = professionalUserAccountAssignmentCsvModel.getUserEmail();
       
        Organisation organisation = new Organisation();
        organisation.setName(orgName);
        organisation = organisationService.create(organisation);
        
        ProfessionalUser professionalUser = new ProfessionalUser();
        professionalUser.setUserId(userEmail);
        professionalUser.setEmail(userEmail);
        professionalUser = professionalUserService.create(professionalUser);
        
        PaymentAccount paymentAccount = paymentAccountService.create(pbaNumber, organisation.getUuid());

        professionalUserService.assignPaymentAccount(professionalUser.getUserId(), paymentAccount.getUuid());
        
        return professionalUser;
    }
}
