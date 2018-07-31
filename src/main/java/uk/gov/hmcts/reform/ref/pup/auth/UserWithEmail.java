package uk.gov.hmcts.reform.ref.pup.auth;

import uk.gov.hmcts.reform.auth.checker.core.user.User;

import java.util.Set;

public class UserWithEmail extends User {

    private final String email;
    
    public UserWithEmail(String principleId, Set<String> roles, String email) {
        super(principleId, roles);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
