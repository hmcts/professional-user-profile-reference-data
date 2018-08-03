package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Entity
public class Assignment extends AbstractDomain {

    @NotNull
    @ManyToOne
    private PaymentAccount paymentAccount;

    @NotNull
    @ManyToOne
    private Address address;

    @NotNull
    @ManyToOne
    private ProfessionalUser professionalUser;

}
