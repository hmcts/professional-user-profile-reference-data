package uk.gov.hmcts.reform.ref.pup.auth;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserWithEmailTokenDetails {

    private final String id;
    private final String email;
    private final Set<String> roles;

}
