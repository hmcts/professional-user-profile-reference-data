package uk.gov.hmcts.reform.ref.pup.converter;

import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountDto;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PaymentAccountConverterTest {
    
    PaymentAccountConverter paymentAccountConverter = new PaymentAccountConverter();
    
    @Test
    public void shouldReturnNullIsObjectIsNull() throws Exception {

        PaymentAccountDto apply = paymentAccountConverter.apply(null);

        assertThat(apply, equalTo(null));
    }

    @Test
    public void dataShouldMatch() throws Exception {

        UUID randomUuid = UUID.randomUUID();
        PaymentAccount paymentAccount = new PaymentAccount();
        paymentAccount.setUuid(randomUuid);
        paymentAccount.setPbaNumber("DUMMY_PBA_NUMBER");
        PaymentAccountDto paymentAccountDto = paymentAccountConverter.apply(paymentAccount);

        assertThat(paymentAccountDto.getPbaNumber(), equalTo("DUMMY_PBA_NUMBER"));
        assertThat(paymentAccountDto.getUuid(), equalTo(randomUuid));
    }
}
