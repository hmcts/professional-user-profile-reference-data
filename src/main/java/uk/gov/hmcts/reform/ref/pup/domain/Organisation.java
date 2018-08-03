package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Organisation extends AbstractDomain {

    private String name;

    @Enumerated(EnumType.STRING)
    private OrganisationType organisationType;

    @OneToOne
    private Organisation organisation;

    @OneToMany(mappedBy = "organisation")
    private Set<PaymentAccount> paymentAccounts = new HashSet<>();

    @OneToMany(mappedBy = "organisation")
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "organisation")
    private Set<ProfessionalUser> professionalUsers = new HashSet<>();

}
