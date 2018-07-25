package uk.gov.hmcts.reform.ref.pup.domain;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PaymentAccountTypeTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnlyOnTheUuid() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        PaymentAccountType paymentAccountType1 = new PaymentAccountType();
        paymentAccountType1.setUuid(randomUuid);
        paymentAccountType1.setName("DUMMY");
        
        PaymentAccountType paymentAccountType2 = new PaymentAccountType();
        paymentAccountType2.setUuid(randomUuid);
        paymentAccountType2.setName("DUMMY2");
        

        assertThat(paymentAccountType2, equalTo(paymentAccountType1));
        assertThat(paymentAccountType2.hashCode(), equalTo(paymentAccountType1.hashCode()));
    }
    

    @Test
    public void toStringShouldReturnAStringWithTheUuidInside() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        PaymentAccountType paymentAccountType = new PaymentAccountType();
        paymentAccountType.setUuid(randomUuid);
        paymentAccountType.setName("DUMMY");


        assertThat(paymentAccountType.toString(), containsString(randomUuid.toString()));
    }

}
