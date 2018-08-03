package uk.gov.hmcts.reform.ref.pup.dto;

import uk.gov.hmcts.reform.ref.pup.domain.OrganisationType;

import lombok.Data;

@Data
public class OrganisationCreation {

    private OrganisationType type;

    private String name;

}
