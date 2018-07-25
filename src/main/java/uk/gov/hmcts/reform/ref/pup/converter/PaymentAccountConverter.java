package uk.gov.hmcts.reform.ref.pup.converter;

import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountDto;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PaymentAccountConverter implements Function<PaymentAccount, PaymentAccountDto> {

    @Override
    public PaymentAccountDto apply(PaymentAccount source) {
        if (source == null) {
            return null;
        }
        
        return PaymentAccountDto.builder()
                .uuid(source.getUuid())
                .pbaNumber(source.getPbaNumber())
                .build();
    }

}