package uk.gov.hmcts.reform.ref.pup.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentAccountCreation {

    private String pbaNumber;

    private UUID organisationId;
}
