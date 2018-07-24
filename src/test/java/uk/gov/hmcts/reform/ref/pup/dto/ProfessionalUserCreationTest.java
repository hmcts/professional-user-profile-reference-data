package uk.gov.hmcts.reform.ref.pup.dto;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProfessionalUserCreationTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnField() throws Exception {

        ProfessionalUserCreation professionalUserCreation1 = new ProfessionalUserCreation();
        professionalUserCreation1.setEmail("DUMMY");
        professionalUserCreation1.setFirstName("DUMMY");        
        professionalUserCreation1.setPhoneNumber("DUMMY");
        professionalUserCreation1.setSurname("DUMMY");
        professionalUserCreation1.setUserId("DUMMY");
        
        ProfessionalUserCreation professionalUserCreation2 = new ProfessionalUserCreation();
        professionalUserCreation2.setEmail("DUMMY");
        professionalUserCreation2.setFirstName("DUMMY");        
        professionalUserCreation2.setPhoneNumber("DUMMY");
        professionalUserCreation2.setSurname("DUMMY");
        professionalUserCreation2.setUserId("DUMMY");
        

        assertThat(professionalUserCreation1, equalTo(professionalUserCreation2));
        assertThat(professionalUserCreation1.hashCode(), equalTo(professionalUserCreation2.hashCode()));
    }

    @Test
    public void toStringShouldReturnAStringWithAllFieldInside() throws Exception {

        ProfessionalUserCreation professionalUserCreation = new ProfessionalUserCreation();
        professionalUserCreation.setEmail("DUMMY@DUMMY.com");
        professionalUserCreation.setFirstName("DUMMY_FIRST_NAME");        
        professionalUserCreation.setPhoneNumber("DUMMY_PHONE_NUMBER");
        professionalUserCreation.setSurname("DUMMY_SURNAME");
        professionalUserCreation.setUserId("DUMMY_USER_ID");

        assertThat(professionalUserCreation.toString(), containsString("DUMMY@DUMMY.com"));
        assertThat(professionalUserCreation.toString(), containsString("DUMMY_FIRST_NAME"));
        assertThat(professionalUserCreation.toString(), containsString("DUMMY_PHONE_NUMBER"));
        assertThat(professionalUserCreation.toString(), containsString("DUMMY_SURNAME"));
        assertThat(professionalUserCreation.toString(), containsString("DUMMY_USER_ID"));
    }
}
