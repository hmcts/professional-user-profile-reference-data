package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Address extends AbstractDomain {

    private String addressLine1; // number

    private String addressLine2; // Street Name

    private String addressLine3; // Other

    private String city; // St Albans

    private String county; // Hertfordshire

    private String country; //England

    private String postcode;

    @ManyToOne
    private Organisation organisation;

    @ManyToOne
    private AddressType addressType;
}
