package uk.gov.hmcts.reform.pup.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PayByAccountNumber {

    @Id
    private String pbaNumber;

    public PayByAccountNumber(String pbaNumber) {
        this.pbaNumber = pbaNumber;
    }

    public String getPbaNumber() {
        return pbaNumber;
    }

    public void setPbaNumber(String pbaNumber) {
        this.pbaNumber = pbaNumber;
    }
}
