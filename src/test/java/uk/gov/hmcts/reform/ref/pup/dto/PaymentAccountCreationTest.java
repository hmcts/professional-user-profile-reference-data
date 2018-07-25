package uk.gov.hmcts.reform.ref.pup.dto;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PaymentAccountCreationTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnField() throws Exception {

        UUID randomUuid = UUID.randomUUID();

        PaymentAccountCreation paymentAccountCreation1 = new PaymentAccountCreation();
        paymentAccountCreation1.setPbaNumber("DUMMY");
        paymentAccountCreation1.setOrganisationId(randomUuid);
        
        PaymentAccountCreation paymentAccountCreation2 = new PaymentAccountCreation();
        paymentAccountCreation2.setPbaNumber("DUMMY");
        paymentAccountCreation2.setOrganisationId(randomUuid);
        

        assertThat(paymentAccountCreation1, equalTo(paymentAccountCreation2));
        assertThat(paymentAccountCreation1.hashCode(), equalTo(paymentAccountCreation2.hashCode()));
    }

    @Test
    public void toStringShouldReturnAStringWithAllFieldInside() throws Exception {

        UUID randomUuid = UUID.randomUUID();
        
        PaymentAccountCreation paymentAccountCreation = new PaymentAccountCreation();
        paymentAccountCreation.setPbaNumber("DUMMY_PBA_NUMBER");
        paymentAccountCreation.setOrganisationId(randomUuid);

        assertThat(paymentAccountCreation.toString(), containsString("DUMMY_PBA_NUMBER"));
        assertThat(paymentAccountCreation.toString(), containsString(randomUuid.toString()));
    }
}
