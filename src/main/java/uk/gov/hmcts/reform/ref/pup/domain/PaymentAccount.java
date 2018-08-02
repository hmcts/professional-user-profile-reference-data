package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "pbanumber"))
public class PaymentAccount extends AbstractDomain {

    private String pbaNumber;

    @Enumerated(EnumType.STRING)
    private PaymentAccountType paymentAccountType;

    @ManyToMany
    private Set<ProfessionalUser> professionalUser = new HashSet<>();

    @ManyToOne
    private Organisation organisation;

}
