package uk.gov.hmcts.reform.ref.pup.domain;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "pbanumber"))
public class PaymentAccount {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    private String pbaNumber; // "PBA123"

    @ManyToOne
    private PaymentAccountType pbaType;

    @OneToMany(mappedBy = "paymentAccount")
    private Set<ProfessionalUserAccountAssignment> accountAssignments;

    @ManyToOne
    private Organisation organisation;

}
