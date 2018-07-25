package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Organisation extends AbstractDomain {

    private String name;

    @ManyToOne
    private OrganisationType organisationType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
    private Set<PaymentAccount> pbas = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organisation")
    private Set<Address> addresses = new HashSet<>();

}
