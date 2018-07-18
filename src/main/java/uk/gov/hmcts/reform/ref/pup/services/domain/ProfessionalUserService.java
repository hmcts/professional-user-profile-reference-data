package uk.gov.hmcts.reform.ref.pup.services.domain;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProfessionalUserService {

    private final ProfessionalUserRepository professionalUserRepository;

    @Autowired
    public ProfessionalUserService(ProfessionalUserRepository professionalUserRepository) {
        this.professionalUserRepository = professionalUserRepository;
    }

    public ProfessionalUser createProfessionalUserWithEmail(String email) throws ApplicationException {
        Optional<ProfessionalUser> professionalUsers = professionalUserRepository.findOneByEmail(email);
        if (professionalUsers.isPresent()) {
            throw new ApplicationException("message");
        }
        
        ProfessionalUser professionalUser = new ProfessionalUser();
        professionalUser.setUserId(email);
        professionalUser.setEmail(email);
        
        return createProfessionalUser(professionalUser);
    }

    public ProfessionalUser createProfessionalUser(final ProfessionalUser professionalUserProfile) {
        return professionalUserRepository.save(professionalUserProfile);
    }

    public ProfessionalUser retrieveProfessionalUser(String userId) {
        return professionalUserRepository.findOneByUserId(userId).orElse(null);
    }

    public void deleteProfessionalUser(String userId) {
        
        professionalUserRepository.deleteByUserId(userId);
    }
}
