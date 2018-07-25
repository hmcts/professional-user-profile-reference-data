package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "email" }))
public class ProfessionalUser extends AbstractDomain {

    private String userId;

    private String firstName;

    private String surname;

    private String email;

    private String phoneNumber;

    @ManyToMany
    private Set<PaymentAccount> accountAssignments = new HashSet<>();

}
