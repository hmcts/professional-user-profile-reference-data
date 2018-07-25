package uk.gov.hmcts.reform.ref.pup.converter;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserDto;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProfessionalUserConverter implements Function<ProfessionalUser, ProfessionalUserDto> {

    @Override
    public ProfessionalUserDto apply(ProfessionalUser source) {
        if (source == null) {
            return null;
        }
        
        return ProfessionalUserDto.builder()
                .uuid(source.getUuid())
                .userId(source.getUserId())
                .firstName(source.getFirstName())
                .surname(source.getSurname())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .build();
    }
}