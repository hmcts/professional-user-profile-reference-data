package uk.gov.hmcts.reform.pup.domain;

public class ProfessionalUserProfile {

    private String loginId;
    private String proOrgId;
    private String pbaRefs;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile2fa;
    private String roles;


    public ProfessionalUserProfile(String loginId) {
        this.loginId = loginId;
    }

    public ProfessionalUserProfile() {
    }

    public String getLoginId() { return loginId; }
    public String getProOrgId() { return proOrgId; }
    public String getPbaRefs() { return pbaRefs; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getMobile2fa() { return mobile2fa; }
    public String getRoles() { return roles; }

    public void setLoginId(String loginId) { this.loginId = loginId; }
    public void setProOrgId(String proOrgId) { this.proOrgId = proOrgId; }
    public void setPbaRefs(String pbaRefs) { this.pbaRefs = pbaRefs; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setMobile2fa(String mobile2fa) { this.mobile2fa = mobile2fa; }
    public void setRoles(String roles) { this.roles = roles; }
}
