package uk.gov.hmcts.reform.ref.pup.services.impl;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.OrganisationType;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.repository.PaymentAccountRepository;
import uk.gov.hmcts.reform.ref.pup.services.OrganisationService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PaymentAccountServiceImplTest {

    @Mock
    private PaymentAccountRepository paymentAccountRepository;
    
    @Mock
    private OrganisationService organisationService;
    
    @InjectMocks
    private PaymentAccountServiceImpl paymentAccountService;

    private String dummyPbaNumber;
    
    private Organisation testOrganisation;

    @Before
    public void setUp() {

        testOrganisation = createFakeOrganisation();
        dummyPbaNumber = "DUMMY";
    }
    
    private Organisation createFakeOrganisation() {
        Organisation fakeTestOrganisation = new Organisation();
        fakeTestOrganisation.setName("DUMMY");
        fakeTestOrganisation.setOrganisationType(new OrganisationType());
        fakeTestOrganisation.setUuid(UUID.randomUUID());
        return fakeTestOrganisation;
    }

    private PaymentAccount createFakePaymentAccount(String pbaNumber) {
        PaymentAccount paymentAccount = new PaymentAccount();
        paymentAccount.setPbaNumber(pbaNumber);
        paymentAccount.setOrganisation(createFakeOrganisation());
        return paymentAccount;
    }

    @Test
    public void create() throws ApplicationException {
        when(paymentAccountRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(organisationService.retrieve(testOrganisation.getUuid())).thenReturn(Optional.of(testOrganisation));
        
        PaymentAccount created = paymentAccountService.create(dummyPbaNumber, testOrganisation.getUuid());

        assertThat(created.getOrganisation(), equalTo(testOrganisation));
        assertThat(created.getPbaNumber(), equalTo(dummyPbaNumber));
    }
    
    @Test(expected = ApplicationException.class)
    public void create_withAnInexistantOrganisationShouldReturnAnException() throws ApplicationException {
        when(paymentAccountRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(organisationService.retrieve(testOrganisation.getUuid())).thenReturn(Optional.empty());
        
        paymentAccountService.create(dummyPbaNumber, testOrganisation.getUuid());
    }

    @Test
    public void retrieve() throws ApplicationException {
        when(paymentAccountRepository.findByPbaNumber(dummyPbaNumber)).thenAnswer(i -> Optional.of(createFakePaymentAccount(i.getArgument(0))));

        Optional<PaymentAccount> retrieve = paymentAccountService.retrieve(dummyPbaNumber);

        assertThat(retrieve.get().getPbaNumber(), equalTo(dummyPbaNumber));
    }

    @Test
    public void delete() throws ApplicationException {

        paymentAccountService.delete(dummyPbaNumber);

        verify(paymentAccountRepository, only()).deleteByPbaNumber(dummyPbaNumber);
    }
    
}
