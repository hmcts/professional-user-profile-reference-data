package uk.gov.hmcts.reform.ref.pup.converter;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrganisationConverter implements Function<Organisation, OrganisationDto> {

    private final AddressConverter addressConverter;

    @Autowired
    public OrganisationConverter(AddressConverter addressConverter) {
        this.addressConverter = addressConverter;
    }

    @Override
    public OrganisationDto apply(Organisation source) {
        if (source == null) {
            return null;
        }

        return OrganisationDto.builder()
                .uuid(source.getUuid())
                .name(source.getName())
                .addresses(source.getAddresses().stream().map(addressConverter).collect(Collectors.toList()))
                .build();
    }

}