package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAccount {

    @Id
    @Getter
    @Setter
    private String pbaNumber; //"PBA123"

    @Getter
    @Setter
    private PaymentAccountType pbaType;

    @Getter
    @Setter
    @OneToMany(mappedBy="paymentAccount")
    private Set<ProfessionalUserAccountAssignment> accountAssignments;

    @Getter
    @Setter
    @ManyToOne
    private Organisation organisation;

}
