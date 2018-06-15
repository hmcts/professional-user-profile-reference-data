package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfessionalUserAccountAssignmentCsvDTO {

    @Getter
    @Setter
    private String orgName;

    @Getter
    @Setter
    private String pbaNumber;

    @Getter
    @Setter
    private String userEmail;

}
