package uk.gov.hmcts.reform.ref.pup.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class OrganisationDto {

    private UUID uuid;

    private String name;

}
