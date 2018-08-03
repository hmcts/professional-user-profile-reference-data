package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Address extends AbstractDomain {

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String city;

    private String county;

    private String country;

    private String postcode;

    @NotNull
    @ManyToOne
    private Organisation organisation;

    @Enumerated(EnumType.STRING)
    protected AddressType addressType;
}
