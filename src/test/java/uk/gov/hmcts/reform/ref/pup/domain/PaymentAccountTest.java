package uk.gov.hmcts.reform.ref.pup.domain;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PaymentAccountTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnlyOnTheUuid() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        PaymentAccount paymentAccount1 = new PaymentAccount();
        paymentAccount1.setUuid(randomUuid);
        paymentAccount1.setPbaNumber("DUMMY");
        
        PaymentAccount paymentAccount2 = new PaymentAccount();
        paymentAccount2.setUuid(randomUuid);
        paymentAccount2.setPbaNumber("DUMMY2");
        

        assertThat(paymentAccount2, equalTo(paymentAccount1));
        assertThat(paymentAccount2.hashCode(), equalTo(paymentAccount1.hashCode()));
    }

    @Test
    public void toStringShouldReturnAStringWithTheUuidInside() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        PaymentAccount paymentAccount = new PaymentAccount();
        paymentAccount.setUuid(randomUuid);
        paymentAccount.setPbaNumber("DUMMY");


        assertThat(paymentAccount.toString(), containsString(randomUuid.toString()));
    }
    
}
