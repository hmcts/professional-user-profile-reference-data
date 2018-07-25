package uk.gov.hmcts.reform.ref.pup.converter;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserDto;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProfessionalUserConverterTest {
    
    ProfessionalUserConverter professionalUserConverter = new ProfessionalUserConverter();
    
    @Test
    public void shouldReturnNullIsObjectIsNull() throws Exception {

        ProfessionalUserDto apply = professionalUserConverter.apply(null);

        assertThat(apply, equalTo(null));
    }

    @Test
    public void dataShouldMatch() throws Exception {

        UUID randomUuid = UUID.randomUUID();
        ProfessionalUser professionalUser = new ProfessionalUser();
        professionalUser.setUuid(randomUuid);
        professionalUser.setEmail("DUMMY_EMAIL");
        professionalUser.setFirstName("DUMMY_FIRST_NAME");
        professionalUser.setPhoneNumber("DUMMY_PHONE_NUMBER");
        professionalUser.setSurname("DUMMY_SURNAME");
        professionalUser.setUserId("DUMMY_USER_ID");
        
        ProfessionalUserDto professionalUserDto = professionalUserConverter.apply(professionalUser);

        assertThat(professionalUserDto.getFirstName(), equalTo("DUMMY_FIRST_NAME"));
        assertThat(professionalUserDto.getEmail(), equalTo("DUMMY_EMAIL"));
        assertThat(professionalUserDto.getPhoneNumber(), equalTo("DUMMY_PHONE_NUMBER"));
        assertThat(professionalUserDto.getSurname(), equalTo("DUMMY_SURNAME"));
        assertThat(professionalUserDto.getUuid(), equalTo(randomUuid));
    }
}
