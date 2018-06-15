package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentAccountType {

    @Id
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @OneToMany(mappedBy="pbaType")
    private Set<PaymentAccount> paymentAccounts;
}
