package uk.gov.hmcts.reform.ref.pup.domain;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "PBANUMBER"))
public class PaymentAccount {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Getter
    @Setter
    private UUID uuid;

    @Getter
    @Setter
    private String pbaNumber; //"PBA123"

    @Getter
    @Setter
    @ManyToOne
    private PaymentAccountType pbaType;

    @Getter
    @Setter
    @OneToMany(mappedBy = "paymentAccount")
    @ToString.Exclude
    private Set<ProfessionalUserAccountAssignment> accountAssignments;

    @Getter
    @Setter
    @ManyToOne
    private Organisation organisation;

}
