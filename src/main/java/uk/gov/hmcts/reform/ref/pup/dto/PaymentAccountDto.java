package uk.gov.hmcts.reform.ref.pup.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class PaymentAccountDto {

    private UUID uuid;

    private String pbaNumber;

    private UUID organisationId;
}
