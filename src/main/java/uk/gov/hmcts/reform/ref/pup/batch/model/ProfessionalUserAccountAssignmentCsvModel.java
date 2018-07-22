package uk.gov.hmcts.reform.ref.pup.batch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfessionalUserAccountAssignmentCsvModel {

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
