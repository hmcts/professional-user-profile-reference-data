package uk.gov.hmcts.reform.ref.pup.dto;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AddressCreationTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnField() throws Exception {

        AddressCreation addressCreation1 = new AddressCreation();
        addressCreation1.setAddressLine1("DUMMY");
        addressCreation1.setAddressLine2("DUMMY");
        addressCreation1.setAddressLine3("DUMMY");
        addressCreation1.setCity("DUMMY");
        addressCreation1.setCountry("DUMMY");
        addressCreation1.setCounty("DUMMY");
        addressCreation1.setPostcode("DUMMY");
        
        AddressCreation addressCreation2 = new AddressCreation();
        addressCreation2.setAddressLine1("DUMMY");
        addressCreation2.setAddressLine2("DUMMY");
        addressCreation2.setAddressLine3("DUMMY");
        addressCreation2.setCity("DUMMY");
        addressCreation2.setCountry("DUMMY");
        addressCreation2.setCounty("DUMMY");
        addressCreation2.setPostcode("DUMMY");
        

        assertThat(addressCreation2, equalTo(addressCreation1));
        assertThat(addressCreation2.hashCode(), equalTo(addressCreation1.hashCode()));
    }

    @Test
    public void toStringShouldReturnAStringWithAllFieldInside() throws Exception {

        AddressCreation addressCreation = new AddressCreation();
        addressCreation.setAddressLine1("DUMMY");
        addressCreation.setAddressLine2("DUMMY");
        addressCreation.setAddressLine3("DUMMY");
        addressCreation.setCity("DUMMY");
        addressCreation.setCountry("DUMMY");
        addressCreation.setCounty("DUMMY");
        addressCreation.setPostcode("DUMMY");

        assertThat(addressCreation.toString(), containsString("DUMMY"));
    }
}
