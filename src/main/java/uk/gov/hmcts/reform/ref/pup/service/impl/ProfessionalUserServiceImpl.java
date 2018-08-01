package uk.gov.hmcts.reform.ref.pup.service.impl;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException.ApplicationErrorCode;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserRepository;
import uk.gov.hmcts.reform.ref.pup.service.ProfessionalUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProfessionalUserServiceImpl implements ProfessionalUserService {

    private final ProfessionalUserRepository professionalUserRepository;

    @Autowired
    public ProfessionalUserServiceImpl(ProfessionalUserRepository professionalUserRepository) {
        this.professionalUserRepository = professionalUserRepository;
    }

    @Override
    public ProfessionalUser create(final ProfessionalUserCreation professionalUserInput) throws ApplicationException {

        Optional<ProfessionalUser> professionalUsers = professionalUserRepository.findOneByEmail(professionalUserInput.getEmail());
        if (professionalUsers.isPresent()) {
            throw new ApplicationException(ApplicationErrorCode.PROFESSIONAL_USER_ID_IN_USE);
        }

        ProfessionalUser professionalUser = new ProfessionalUser();
        professionalUser.setEmail(professionalUserInput.getEmail());
        professionalUser.setFirstName(professionalUserInput.getFirstName());
        professionalUser.setPhoneNumber(professionalUserInput.getPhoneNumber());
        professionalUser.setSurname(professionalUserInput.getSurname());
        professionalUser.setUserId(professionalUserInput.getUserId());

        return professionalUserRepository.save(professionalUser);
    }

    @Override
    public Optional<ProfessionalUser> retrieve(String userId) throws ApplicationException {
        return professionalUserRepository.findOneByUserId(userId);
    }

    @Override
    public void delete(String userId) throws ApplicationException {
        professionalUserRepository.deleteByUserId(userId);
    }

}
