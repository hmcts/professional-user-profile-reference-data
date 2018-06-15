package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Getter
    @Setter
    private UUID uuid;

    @Getter
    @Setter
    private String addressLine1; // number

    @Getter
    @Setter
    private String addressLine2; // Street Name

    @Getter
    @Setter
    private String addressLine3; // Other

    @Getter
    @Setter
    private String city; // St Albans

    @Getter
    @Setter
    private String county; // Hertfordshire

    @Getter
    @Setter
    private String country; //England

    @Getter
    @Setter
    private String postcode;

    @Getter
    @Setter
    @ManyToOne()
    private Organisation organisation;

    @Getter
    @Setter
    @ManyToOne()
    private AddressType addressType;
}
