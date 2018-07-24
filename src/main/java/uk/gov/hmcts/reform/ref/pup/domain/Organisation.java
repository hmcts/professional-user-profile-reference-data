package uk.gov.hmcts.reform.ref.pup.domain;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Organisation {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    private String name;

    @ManyToOne
    private OrganisationType organisationType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
    private Set<PaymentAccount> pbas = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
    private Set<Address> addresses = new HashSet<>();

}
