package uk.gov.hmcts.reform.ref.pup.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ProfessionalUserCreation {

    @NotEmpty
    private String userId;

    private String firstName;

    private String surname;

    private String email;

    private String phoneNumber;

    @NotEmpty
    private String organisationId;

}
