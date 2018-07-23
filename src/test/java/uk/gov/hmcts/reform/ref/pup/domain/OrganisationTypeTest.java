package uk.gov.hmcts.reform.ref.pup.domain;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class OrganisationTypeTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnlyOnTheUuid() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        OrganisationType organisationType1 = new OrganisationType();
        organisationType1.setUuid(randomUuid);
        organisationType1.setName("DUMMY");
        
        OrganisationType organisationType2 = new OrganisationType();
        organisationType2.setUuid(randomUuid);
        organisationType2.setName("DUMMY2");
        

        assertThat(organisationType2, equalTo(organisationType1));
        assertThat(organisationType2.hashCode(), equalTo(organisationType1.hashCode()));
    }

    @Test
    public void toStringShouldReturnAStringWithTheUuidInside() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        OrganisationType organisationType = new OrganisationType();
        organisationType.setUuid(randomUuid);
        organisationType.setName("DUMMY");

        assertThat(organisationType.toString(), containsString(randomUuid.toString()));
    }
}
