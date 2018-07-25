package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "pbanumber"))
public class PaymentAccount extends AbstractDomain {

    private String pbaNumber; // "PBA123"

    @ManyToOne
    private PaymentAccountType pbaType;

    @ManyToMany
    private Set<ProfessionalUser> professionalUser = new HashSet<>();

    @ManyToOne
    private Organisation organisation;

}
