package uk.gov.hmcts.reform.ref.pup.service.adaptor;

import uk.gov.hmcts.reform.ref.pup.converter.ProfessionalUserConverter;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserCreation;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserDto;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.service.ProfessionalUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProfessionalUserServiceAdaptor {
    
    private final ProfessionalUserService professionalUserService;
    private final ProfessionalUserConverter professionalUserConverter;

    @Autowired
    public ProfessionalUserServiceAdaptor(ProfessionalUserService professionalUserService, ProfessionalUserConverter professionalUserConverter) {
        this.professionalUserService = professionalUserService;
        this.professionalUserConverter = professionalUserConverter;
    }
    
    public ProfessionalUserDto create(ProfessionalUserCreation professionalUser) throws ApplicationException {
        return professionalUserConverter.apply(professionalUserService.create(professionalUser));
    }

    public Optional<ProfessionalUserDto> retrieve(String userId) throws ApplicationException {
        Optional<ProfessionalUser> retrieve = professionalUserService.retrieve(userId);
        if (retrieve.isPresent()) {
            return Optional.of(professionalUserConverter.apply(retrieve.get()));
        } else {
            return Optional.empty();
        }
    }

    public void delete(String userId) throws ApplicationException {
        professionalUserService.delete(userId);
    }

    public void assignPaymentAccount(String userId, UUID paymentAccountId) throws ApplicationException {
        professionalUserService.assignPaymentAccount(userId, paymentAccountId);
    }

    public void unassignPaymentAccount(String userId, UUID paymentAccountId) throws ApplicationException {
        professionalUserService.unassignPaymentAccount(userId, paymentAccountId);
    }

}