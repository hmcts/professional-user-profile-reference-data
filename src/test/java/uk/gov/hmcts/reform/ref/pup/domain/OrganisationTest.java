package uk.gov.hmcts.reform.ref.pup.domain;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class OrganisationTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnlyOnTheUuid() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        Organisation organisation1 = new Organisation();
        organisation1.setUuid(randomUuid);
        organisation1.setName("DUMMY");
        
        Organisation organisation2 = new Organisation();
        organisation2.setUuid(randomUuid);
        organisation2.setName("DUMMY2");
        

        assertThat(organisation2, equalTo(organisation1));
        assertThat(organisation2.hashCode(), equalTo(organisation1.hashCode()));
    }


    @Test
    public void toStringShouldReturnAStringWithTheUuidInside() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        Organisation organisation = new Organisation();
        organisation.setUuid(randomUuid);
        organisation.setName("DUMMY");

        assertThat(organisation.toString(), containsString(randomUuid.toString()));
    }
    
}
