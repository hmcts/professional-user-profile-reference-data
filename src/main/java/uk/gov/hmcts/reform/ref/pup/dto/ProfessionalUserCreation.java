package uk.gov.hmcts.reform.ref.pup.dto;

import lombok.Data;

@Data
public class ProfessionalUserCreation {

    private String userId;

    private String firstName;

    private String surname;

    private String email;

    private String phoneNumber;

}
