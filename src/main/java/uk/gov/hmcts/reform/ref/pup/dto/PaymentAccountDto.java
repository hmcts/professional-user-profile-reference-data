package uk.gov.hmcts.reform.ref.pup.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class PaymentAccountDto {

    private UUID uuId;

    private String pbaNumber;

    private UUID organisationId;
}
