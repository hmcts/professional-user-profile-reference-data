package uk.gov.hmcts.reform.ref.pup.converter;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationDto;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OrganisationConverter implements Function<Organisation, OrganisationDto> {

    @Override
    public OrganisationDto apply(Organisation source) {
        if (source == null) {
            return null;
        }
        
        return OrganisationDto.builder()
                .uuid(source.getUuid())
                .name(source.getName())
                .build();
    }

}