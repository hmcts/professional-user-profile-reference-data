package uk.gov.hmcts.reform.ref.pup.domain;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Data
@Entity
@EqualsAndHashCode(of = "uuid")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "email" }))
public class ProfessionalUser {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    private String userId;

    private String firstName;

    private String surname;

    private String email;

    private String phoneNumber;

    @ManyToMany
    private Set<PaymentAccount> accountAssignments = new HashSet<>();

}
