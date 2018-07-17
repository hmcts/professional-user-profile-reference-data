package uk.gov.hmcts.reform.ref.pup.domain;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

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
