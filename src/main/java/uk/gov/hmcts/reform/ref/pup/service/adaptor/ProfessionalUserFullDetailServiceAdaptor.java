package uk.gov.hmcts.reform.ref.pup.service.adaptor;

import uk.gov.hmcts.reform.ref.pup.converter.ProfessionalUserFullDetailConverter;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserFullDetailDto;
import uk.gov.hmcts.reform.ref.pup.exception.ApplicationException;
import uk.gov.hmcts.reform.ref.pup.service.ProfessionalUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProfessionalUserFullDetailServiceAdaptor {

    private final ProfessionalUserService professionalUserService;
    private final ProfessionalUserFullDetailConverter professionalUserConverter;

    @Autowired
    public ProfessionalUserFullDetailServiceAdaptor(ProfessionalUserService professionalUserService, ProfessionalUserFullDetailConverter professionalUserConverter) {
        this.professionalUserService = professionalUserService;
        this.professionalUserConverter = professionalUserConverter;
    }

    public Optional<ProfessionalUserFullDetailDto> retrieve(String userId) throws ApplicationException {
        Optional<ProfessionalUser> retrieve = professionalUserService.retrieve(userId);
        if (retrieve.isPresent()) {
            return Optional.of(professionalUserConverter.apply(retrieve.get()));
        } else {
            return Optional.empty();
        }
    }
}