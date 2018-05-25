package uk.gov.hmcts.reform.pup.services;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.pup.domain.ProfessionalUserProfile;

import java.util.UUID;

@Service
public class ProfessionalUserProfileService {


    public ProfessionalUserProfile newSession(final ProfessionalUserProfile professionalUserProfile) {
        final UUID uuid = UUID.randomUUID();
        professionalUserProfile.setProOrgId("id");
        return professionalUserProfile;
    }

}
