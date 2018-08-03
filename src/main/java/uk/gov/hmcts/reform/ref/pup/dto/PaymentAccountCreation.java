package uk.gov.hmcts.reform.ref.pup.dto;

import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccountType;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentAccountCreation {

    private String pbaNumber;

    private PaymentAccountType type;

    private UUID organisationId;
}
