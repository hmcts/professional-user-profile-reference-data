package uk.gov.hmcts.reform.ref.pup.converter;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserFullDetailDto;

import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProfessionalUserFullDetailConverterTest {

    ProfessionalUserFullDetailConverter professionalUserFullDetailConverter = new ProfessionalUserFullDetailConverter(
                                                                                        new OrganisationConverter(new AddressConverter()),
                                                                                        new PaymentAccountConverter());

    @Test
    public void shouldReturnNullIsObjectIsNull() throws Exception {

        ProfessionalUserFullDetailDto apply = professionalUserFullDetailConverter.apply(null);

        assertThat(apply, equalTo(null));
    }

    @Test
    @Ignore // TODO
    public void dataShouldMatch() throws Exception {

        UUID randomUuid = UUID.randomUUID();
        ProfessionalUser professionalUser = new ProfessionalUser();
        professionalUser.setUuid(randomUuid);
        professionalUser.setEmail("DUMMY_EMAIL");
        professionalUser.setFirstName("DUMMY_FIRST_NAME");
        professionalUser.setPhoneNumber("DUMMY_PHONE_NUMBER");
        professionalUser.setSurname("DUMMY_SURNAME");
        professionalUser.setUserId("DUMMY_USER_ID");

        ProfessionalUserFullDetailDto professionalUserFullDetailDto = professionalUserFullDetailConverter.apply(professionalUser);

        assertThat(professionalUserFullDetailDto.getFirstName(), equalTo("DUMMY_FIRST_NAME"));
        assertThat(professionalUserFullDetailDto.getEmail(), equalTo("DUMMY_EMAIL"));
        assertThat(professionalUserFullDetailDto.getPhoneNumber(), equalTo("DUMMY_PHONE_NUMBER"));
        assertThat(professionalUserFullDetailDto.getSurname(), equalTo("DUMMY_SURNAME"));
    }
}
