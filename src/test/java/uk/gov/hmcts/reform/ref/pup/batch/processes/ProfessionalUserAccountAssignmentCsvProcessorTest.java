package uk.gov.hmcts.reform.ref.pup.batch.processes;

import uk.gov.hmcts.reform.ref.pup.batch.model.ProfessionalUserAccountAssignmentCsvModel;
import uk.gov.hmcts.reform.ref.pup.batch.process.ProfessionalUserAccountAssignmentCsvProcessor;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.OrganisationType;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationCreation;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountCreation;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.service.OrganisationService;
import uk.gov.hmcts.reform.ref.pup.service.PaymentAccountService;
import uk.gov.hmcts.reform.ref.pup.service.ProfessionalUserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProfessionalUserAccountAssignmentCsvProcessorTest {

    @Mock
    private OrganisationService organisationService;

    @Mock
    private PaymentAccountService paymentAccountService;

    @Mock
    private ProfessionalUserService professionalUserService;


    @InjectMocks
    private ProfessionalUserAccountAssignmentCsvProcessor professionalUserAccountAssignmentCsv;

    private Organisation testOrganisation;
    private ProfessionalUser testProfessionalUser;
    private PaymentAccount testPaymentAccount;

    @Captor
    ArgumentCaptor<PaymentAccountCreation> paymentAccountRequestCaptor;
    @Captor
    ArgumentCaptor<String> userIdCaptor;
    @Captor
    ArgumentCaptor<UUID> organisationUuidCaptor;
    @Captor
    ArgumentCaptor<ProfessionalUserCreation> professionalUserRequestCaptor;
    @Captor
    ArgumentCaptor<OrganisationCreation> organisationRequestCaptor;

    private ProfessionalUserAccountAssignmentCsvModel testProfessionalUserAccountAssignmentCsvModel;

    @Before
    public void setUp() {

        testOrganisation = createFakeOrganisation();
        testProfessionalUser = createFakeProfessionalUser();
        testPaymentAccount = createFakePaymentAccount();
        testProfessionalUserAccountAssignmentCsvModel = createFakeModel();

    }

    private ProfessionalUser createFakeProfessionalUser() {
        ProfessionalUser firstTestUser = new ProfessionalUser();
        firstTestUser.setEmail("DUMMY@DUMMY.com");
        firstTestUser.setFirstName("DUMMY");
        firstTestUser.setPhoneNumber("DUMMY");
        firstTestUser.setSurname("DUMMY");
        firstTestUser.setUserId("DUMMY");
        firstTestUser.setUuid(UUID.randomUUID());
        firstTestUser.setAccountAssignments(new HashSet<>());
        return firstTestUser;
    }

    private Organisation createFakeOrganisation() {
        Organisation firstTestOrganisation = new Organisation();
        firstTestOrganisation.setName("DUMMY");
        firstTestOrganisation.setOrganisationType(new OrganisationType());
        firstTestOrganisation.setUuid(UUID.randomUUID());
        return firstTestOrganisation;
    }

    private PaymentAccount createFakePaymentAccount() {
        PaymentAccount paymentAccount = new PaymentAccount();
        paymentAccount.setPbaNumber("DUMMY");
        paymentAccount.setUuid(UUID.randomUUID());
        return paymentAccount;
    }

    private ProfessionalUserAccountAssignmentCsvModel createFakeModel() {
        ProfessionalUserAccountAssignmentCsvModel firstTestProfessionalUserAccountAssignmentCsvModel = new ProfessionalUserAccountAssignmentCsvModel();
        firstTestProfessionalUserAccountAssignmentCsvModel.setOrgName("DUMMY");
        firstTestProfessionalUserAccountAssignmentCsvModel.setPbaNumber("DUMMY");
        firstTestProfessionalUserAccountAssignmentCsvModel.setUserEmail("DUMMY@DUMMY.com");
        return firstTestProfessionalUserAccountAssignmentCsvModel;
    }


    @Test
    public void process_happyPath() throws ApplicationException {
        when(organisationService.findOrCreate(any())).thenReturn(testOrganisation);
        when(professionalUserService.findOrCreate(any())).thenReturn(testProfessionalUser);
        doNothing().when(professionalUserService).assignPaymentAccount(any(), any());
        when(paymentAccountService.findOrCreate(any())).thenReturn(testPaymentAccount);

        professionalUserAccountAssignmentCsv.process(testProfessionalUserAccountAssignmentCsvModel);


        verify(organisationService, only()).findOrCreate(organisationRequestCaptor.capture());
        verify(professionalUserService).findOrCreate(professionalUserRequestCaptor.capture());
        verify(professionalUserService).assignPaymentAccount(userIdCaptor.capture(), organisationUuidCaptor.capture());
        verify(paymentAccountService, only()).findOrCreate(paymentAccountRequestCaptor.capture());

        assertThat(organisationRequestCaptor.getValue().getName(), equalTo(testProfessionalUserAccountAssignmentCsvModel.getOrgName()));
        assertThat(professionalUserRequestCaptor.getValue().getEmail(), equalTo(testProfessionalUserAccountAssignmentCsvModel.getUserEmail()));
        assertThat(paymentAccountRequestCaptor.getValue().getPbaNumber(), equalTo(testProfessionalUserAccountAssignmentCsvModel.getPbaNumber()));

    }

}
