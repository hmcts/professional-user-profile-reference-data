package uk.gov.hmcts.reform.ref.pup.service.impl;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserCreation;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException.ApplicationErrorCode;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserRepository;
import uk.gov.hmcts.reform.ref.pup.service.OrganisationService;
import uk.gov.hmcts.reform.ref.pup.service.ProfessionalUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProfessionalUserServiceImpl implements ProfessionalUserService {

    private final ProfessionalUserRepository professionalUserRepository;


    private final OrganisationService organisationService;

    @Autowired
    public ProfessionalUserServiceImpl(ProfessionalUserRepository professionalUserRepository, OrganisationService organisationService) {
        this.professionalUserRepository = professionalUserRepository;
        this.organisationService = organisationService;
    }

    @Override
    public ProfessionalUser create(final ProfessionalUserCreation professionalUserInput) throws ApplicationException {

        Optional<ProfessionalUser> professionalUsers = professionalUserRepository.findOneByEmail(professionalUserInput.getEmail());
        if (professionalUsers.isPresent()) {
            throw new ApplicationException(ApplicationErrorCode.PROFESSIONAL_USER_ID_IN_USE);
        }
        Organisation organisation = organisationService.retrieve(UUID.fromString(professionalUserInput.getOrganisationId()))
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.ORGANISATION_ID_DOES_NOT_EXIST));

        ProfessionalUser professionalUser = new ProfessionalUser();
        professionalUser.setEmail(professionalUserInput.getEmail());
        professionalUser.setFirstName(professionalUserInput.getFirstName());
        professionalUser.setPhoneNumber(professionalUserInput.getPhoneNumber());
        professionalUser.setSurname(professionalUserInput.getSurname());
        professionalUser.setUserId(professionalUserInput.getUserId());
        professionalUser.setOrganisation(organisation);

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
