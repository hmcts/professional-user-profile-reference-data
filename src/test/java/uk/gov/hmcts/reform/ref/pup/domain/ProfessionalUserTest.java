package uk.gov.hmcts.reform.ref.pup.domain;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProfessionalUserTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnlyOnTheUuid() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        ProfessionalUser professionalUser1 = new ProfessionalUser();
        professionalUser1.setUuid(randomUuid);
        professionalUser1.setEmail("DUMMY");
        professionalUser1.setFirstName("DUMMY");        
        professionalUser1.setPhoneNumber("DUMMY");
        professionalUser1.setSurname("DUMMY");
        professionalUser1.setUserId("DUMMY");
        
        ProfessionalUser professionalUser2 = new ProfessionalUser();
        professionalUser2.setUuid(randomUuid);
        professionalUser2.setEmail("DUMMY2");
        professionalUser2.setFirstName("DUMMY2");        
        professionalUser2.setPhoneNumber("DUMMY2");
        professionalUser2.setSurname("DUMMY2");
        professionalUser2.setUserId("DUMMY2");
        

        assertThat(professionalUser2, equalTo(professionalUser1));
        assertThat(professionalUser2.hashCode(), equalTo(professionalUser1.hashCode()));
    }
    
    @Test
    public void toStringShouldReturnAStringWithTheUuidInside() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        ProfessionalUser professionalUser = new ProfessionalUser();
        professionalUser.setUuid(randomUuid);
        professionalUser.setEmail("DUMMY2");
        professionalUser.setFirstName("DUMMY2");        
        professionalUser.setPhoneNumber("DUMMY2");
        professionalUser.setSurname("DUMMY2");
        professionalUser.setUserId("DUMMY2");


        assertThat(professionalUser.toString(), containsString(randomUuid.toString()));
    }

}
