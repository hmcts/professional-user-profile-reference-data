package uk.gov.hmcts.reform.ref.pup.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentAccountDto {

    private UUID uuId;

    private String pbaNumber;

    private UUID organisationId;
}
