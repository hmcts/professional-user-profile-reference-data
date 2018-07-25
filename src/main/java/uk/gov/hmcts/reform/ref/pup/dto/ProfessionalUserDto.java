package uk.gov.hmcts.reform.ref.pup.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;


@Builder
@Getter
public class ProfessionalUserDto {

    private UUID uuid;

    private String userId;

    private String firstName;

    private String surname;

    private String email;

    private String phoneNumber;
}
