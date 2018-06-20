package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(uniqueConstraints=@UniqueConstraint(columnNames="PBANUMBER"))
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
    @OneToMany(mappedBy="paymentAccount")
    @ToString.Exclude
    private Set<ProfessionalUserAccountAssignment> accountAssignments;

    @Getter
    @Setter
    @ManyToOne
    private Organisation organisation;

}
