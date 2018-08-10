package uk.gov.hmcts.reform.ref.pup.converter;

import uk.gov.hmcts.reform.ref.pup.domain.Address;
import uk.gov.hmcts.reform.ref.pup.dto.AddressDto;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AddressConverterTest {

    AddressConverter addressConverter = new AddressConverter();

    @Test
    public void shouldReturnNullIsObjectIsNull() throws Exception {

        AddressDto apply = addressConverter.apply(null);

        assertThat(apply, equalTo(null));
    }

    @Test
    public void dataShouldMatch() throws Exception {

        UUID randomUuid = UUID.randomUUID();

        Address address = new Address();
        address.setUuid(randomUuid);
        address.setAddressLine1("DUMMY_ADDRESS_LINE_1");
        address.setAddressLine2("DUMMY_ADDRESS_LINE_2");
        address.setAddressLine3("DUMMY_ADDRESS_LINE_3");
        address.setCity("DUMMY_CITY");
        address.setCounty("DUMMY_COUNTY");
        address.setCountry("DUMMY_COUNTRY");
        address.setPostcode("DUMMY_POST_CODE");

        AddressDto addressDto = addressConverter.apply(address);

        assertThat(addressDto.getAddressLine1(), equalTo("DUMMY_ADDRESS_LINE_1"));
        assertThat(addressDto.getAddressLine2(), equalTo("DUMMY_ADDRESS_LINE_2"));
        assertThat(addressDto.getAddressLine3(), equalTo("DUMMY_ADDRESS_LINE_3"));
        assertThat(addressDto.getCity(), equalTo("DUMMY_CITY"));
        assertThat(addressDto.getCounty(), equalTo("DUMMY_COUNTY"));
        assertThat(addressDto.getCountry(), equalTo("DUMMY_COUNTRY"));
        assertThat(addressDto.getPostcode(), equalTo("DUMMY_POST_CODE"));
        assertThat(addressDto.getUuid(), equalTo(randomUuid));
    }
}
