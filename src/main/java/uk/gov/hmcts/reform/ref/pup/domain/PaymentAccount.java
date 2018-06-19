package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentAccount {

    @Id
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
