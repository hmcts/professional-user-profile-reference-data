package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "pbanumber"))
public class PaymentAccount extends AbstractDomain {


    @NotEmpty
    private String pbaNumber;

    @Enumerated(EnumType.STRING)
    private PaymentAccountType paymentAccountType;

    @NotNull
    @ManyToOne
    private Organisation organisation;

}
