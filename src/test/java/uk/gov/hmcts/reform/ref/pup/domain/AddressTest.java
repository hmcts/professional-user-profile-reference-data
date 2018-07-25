package uk.gov.hmcts.reform.ref.pup.domain;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AddressTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnlyOnTheUuid() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        Address address1 = new Address();
        address1.setUuid(randomUuid);
        address1.setAddressLine1("DUMMY");
        address1.setAddressLine2("DUMMY");
        address1.setAddressLine3("DUMMY");
        address1.setCity("DUMMY");
        address1.setCounty("DUMMY");
        address1.setCountry("DUMMY");
        address1.setPostcode("DUMMY");
        
        Address address2 = new Address();
        address2.setUuid(randomUuid);
        address2.setAddressLine1("DUMMY2");
        address2.setAddressLine2("DUMMY2");
        address2.setAddressLine3("DUMMY2");
        address2.setCity("DUMMY2");
        address2.setCounty("DUMMY2");
        address2.setCountry("DUMMY2");
        address2.setPostcode("DUMMY2");
        

        assertThat(address2, equalTo(address1));
        assertThat(address2.hashCode(), equalTo(address1.hashCode()));
    }

    @Test
    public void toStringShouldReturnAStringWithTheUuidInside() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        Address address = new Address();
        address.setUuid(randomUuid);
        address.setAddressLine1("DUMMY2");
        address.setAddressLine2("DUMMY2");
        address.setAddressLine3("DUMMY2");
        address.setCity("DUMMY2");
        address.setCounty("DUMMY2");
        address.setCountry("DUMMY2");
        address.setPostcode("DUMMY2");
        

        assertThat(address.toString(), containsString(randomUuid.toString()));
    }
}
