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
public class Organisation {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Getter
    @Setter
    private UUID uuid;

    @Getter
    @Setter
    @Column(unique=true)
    private String name;

    @Getter
    @Setter
    @ManyToOne
    private OrganisationType organisationType;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
    @ToString.Exclude
    private Set<PaymentAccount> pbas;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
    @ToString.Exclude
    private Set<Address> addresses;

}
