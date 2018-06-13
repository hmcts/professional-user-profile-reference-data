package uk.gov.hmcts.reform.ref.pup.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalUser {

    @Id
    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String surname;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String phoneNumber;

    @OneToMany(mappedBy="user")
    private Set<ProfessionalUserAccountAssignment> accountAssignments;

}
