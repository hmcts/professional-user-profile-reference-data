package uk.gov.hmcts.reform.ref.pup.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class ProfessionalUser {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    private String loginId;
    private String proOrgId;
//    private List<PayByAccountNumber> pbaRefs;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile2fa;
    private String roles;


    public ProfessionalUser() {}

    public ProfessionalUser(String loginId) {
        this.loginId = loginId;
    }

    public UUID getUuid() {return uuid;}
    public String getLoginId() { return loginId; }
    public String getProOrgId() { return proOrgId; }
//    public List<PayByAccountNumber> getPbaRefs() { return pbaRefs; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getMobile2fa() { return mobile2fa; }
    public String getRoles() { return roles; }

    public void setUuid(UUID uuid) {this.uuid = uuid;}
    public void setLoginId(String loginId) { this.loginId = loginId; }
    public void setProOrgId(String proOrgId) { this.proOrgId = proOrgId; }
//    public void setPbaRefs(List<PayByAccountNumber> pbaRefs) { this.pbaRefs = pbaRefs; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setMobile2fa(String mobile2fa) { this.mobile2fa = mobile2fa; }
    public void setRoles(String roles) { this.roles = roles; }
}
