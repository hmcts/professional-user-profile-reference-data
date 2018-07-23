package uk.gov.hmcts.reform.ref.pup.domain;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AddressTypeTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnlyOnTheUuid() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        AddressType addressType1 = new AddressType();
        addressType1.setUuid(randomUuid);
        addressType1.setName("DUMMY");
        
        AddressType addressType2 = new AddressType();
        addressType2.setUuid(randomUuid);
        addressType2.setName("DUMMY2");
        

        assertThat(addressType2, equalTo(addressType1));
        assertThat(addressType2.hashCode(), equalTo(addressType1.hashCode()));
    }

    @Test
    public void toStringShouldReturnAStringWithTheUuidInside() throws Exception {
        UUID randomUuid = UUID.randomUUID();

        AddressType addressType = new AddressType();
        addressType.setUuid(randomUuid);
        addressType.setName("DUMMY");

        assertThat(addressType.toString(), containsString(randomUuid.toString()));
    }
    
}
