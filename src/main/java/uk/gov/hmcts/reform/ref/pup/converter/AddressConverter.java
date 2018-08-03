package uk.gov.hmcts.reform.ref.pup.converter;

import uk.gov.hmcts.reform.ref.pup.domain.Address;
import uk.gov.hmcts.reform.ref.pup.dto.AddressDto;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddressConverter implements Function<Address, AddressDto> {

    @Override
    public AddressDto apply(Address source) {
        if (source == null) {
            return null;
        }

        return AddressDto.builder()
                .uuid(source.getUuid())
                .addressLine1(source.getAddressLine1())
                .addressLine2(source.getAddressLine2())
                .addressLine3(source.getAddressLine3())
                .city(source.getCity())
                .country(source.getCountry())
                .county(source.getCounty())
                .postcode(source.getPostcode())
                .build();
    }

}