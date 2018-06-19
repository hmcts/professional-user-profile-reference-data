package uk.gov.hmcts.reform.ref.pup.services.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserRepository;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;

import java.util.List;

@Service
public class ProfessionalUserService {

    private ProfessionalUserRepository professionalUserRepository;

    @Autowired
    public ProfessionalUserService(ProfessionalUserRepository professionalUserRepository) {
        this.professionalUserRepository = professionalUserRepository;
    }

    public ProfessionalUser createProfessionalUserWithEmail(String email) {
        List<ProfessionalUser> professionalUsers = professionalUserRepository.findByEmail(email);
        return professionalUsers.isEmpty() ?
            createProfessionalUser(
                ProfessionalUser
                    .builder()
                    .userId(email)
                    .email(email)
                    .build()
            )
            : professionalUsers.get(0);
    }

    public ProfessionalUser createProfessionalUser(final ProfessionalUser professionalUserProfile) {
        return professionalUserRepository.save(professionalUserProfile);
    }

    public ProfessionalUser retrieveProfessionalUser(String userId) {
        return professionalUserRepository.findById(userId).orElse(null);
    }

    public void deleteProfessionalUser(String userId) {
        professionalUserRepository.deleteById(userId);
    }
}
