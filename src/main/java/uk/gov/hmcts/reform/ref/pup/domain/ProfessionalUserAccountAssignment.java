package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"PAYMENTACCOUNT_UUID","USER_UUID"}))
public class ProfessionalUserAccountAssignment {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Getter
    @Setter
    private UUID uuid;

    @Getter
    @Setter
    @ManyToOne
    private PaymentAccount paymentAccount;

    @Getter
    @Setter
    @ManyToOne
    private ProfessionalUser user;
}
