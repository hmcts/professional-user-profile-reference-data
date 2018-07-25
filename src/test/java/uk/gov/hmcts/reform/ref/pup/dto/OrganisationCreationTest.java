package uk.gov.hmcts.reform.ref.pup.dto;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class OrganisationCreationTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnField() throws Exception {

        OrganisationCreation organisationCreation1 = new OrganisationCreation();
        organisationCreation1.setName("DUMMY");
        
        OrganisationCreation organisationCreation2 = new OrganisationCreation();
        organisationCreation2.setName("DUMMY");
        

        assertThat(organisationCreation1, equalTo(organisationCreation2));
        assertThat(organisationCreation1.hashCode(), equalTo(organisationCreation2.hashCode()));
    }

    @Test
    public void toStringShouldReturnAStringWithAllFieldInside() throws Exception {

        OrganisationCreation organisationCreation = new OrganisationCreation();
        organisationCreation.setName("DUMMY_NAME");

        assertThat(organisationCreation.toString(), containsString("DUMMY_NAME"));
    }
}
