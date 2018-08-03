package uk.gov.hmcts.reform.ref.pup.dto;

import uk.gov.hmcts.reform.ref.pup.domain.AddressType;

import lombok.Data;

@Data
public class AddressCreation {

    private AddressType type;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String city;

    private String county;

    private String country;

    private String postcode;
}
