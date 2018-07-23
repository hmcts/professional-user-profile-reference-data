package uk.gov.hmcts.reform.ref.pup.domain;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@EqualsAndHashCode(of = "uuid")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "pbanumber"))
public class PaymentAccount {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    private String pbaNumber; // "PBA123"

    @ManyToOne
    private PaymentAccountType pbaType;

    @ManyToMany
    private Set<ProfessionalUser> professionalUser;

    @ManyToOne
    private Organisation organisation;

}
