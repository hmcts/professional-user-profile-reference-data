package uk.gov.hmcts.reform.ref.pup.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PaymentAccountAssignment {

    private String userId;

    @NotEmpty
    private String addressId;

}
