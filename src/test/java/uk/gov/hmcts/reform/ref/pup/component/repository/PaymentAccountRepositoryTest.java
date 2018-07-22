package uk.gov.hmcts.reform.ref.pup.component.repository;

import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.repository.PaymentAccountRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PaymentAccountRepositoryTest {

    @Autowired
    private PaymentAccountRepository paymentAccountRepository;

    private PaymentAccount firstTestPaymentAccount;
    private PaymentAccount secondTestPaymentAccount;

    @Before
    public void setUp() {

        // first test payment account
        firstTestPaymentAccount = new PaymentAccount();
        firstTestPaymentAccount.setPbaNumber("123_456");
       
        paymentAccountRepository.save(firstTestPaymentAccount);
        
        // second test payment account
        secondTestPaymentAccount = new PaymentAccount();
        secondTestPaymentAccount.setPbaNumber("123_789");
       
        paymentAccountRepository.save(secondTestPaymentAccount);
        
    }
    
    @After
    public void tearDown() {
        paymentAccountRepository.delete(firstTestPaymentAccount);
        paymentAccountRepository.delete(secondTestPaymentAccount);
    }
    

    @Test
    public void findByPbaNumber_shouldReturnNoPresentIfNoPbANumberIsNotThere() throws Exception {
        Optional<PaymentAccount> findByPbaNumber = paymentAccountRepository.findByPbaNumber("13_456");
        assertFalse(findByPbaNumber.isPresent());
    }
    
    @Test
    public void findByPbaNumber_shouldReturnThePaymentAccountAssociatedWithThePbaNumber() throws Exception {
        Optional<PaymentAccount> findByPbaNumber = paymentAccountRepository.findByPbaNumber("123_456");
        
        assertThat("123_456", equalTo(findByPbaNumber.get().getPbaNumber()));
    }


}
