package uk.gov.hmcts.reform.ref.pup.auth;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserWithEmailTokenDetails {

    private String id;
    private String email;
    private Set<String> roles;

}
