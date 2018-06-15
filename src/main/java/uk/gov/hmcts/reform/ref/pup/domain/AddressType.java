package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressType {

    @Id
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @OneToMany(mappedBy="addressType")
    private Set<Address> addresses;
}
