package uk.gov.hmcts.reform.ref.pup.batch.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProfessionalUserAccountAssignmentCsvModelTest {

    @Test
    public void equalsAndHachCodeShouldBeDependentOnField() throws Exception {

        ProfessionalUserAccountAssignmentCsvModel professionalUserAccountAssignmentCsvModel1 = new ProfessionalUserAccountAssignmentCsvModel();
        professionalUserAccountAssignmentCsvModel1.setOrgName("DUMMY_ORG_NAME");
        professionalUserAccountAssignmentCsvModel1.setPbaNumber("DUMMY_PBA_NUMBER");
        professionalUserAccountAssignmentCsvModel1.setUserEmail("DUMMY@DUMMY.com");
        
        ProfessionalUserAccountAssignmentCsvModel professionalUserAccountAssignmentCsvModel2 = new ProfessionalUserAccountAssignmentCsvModel();
        professionalUserAccountAssignmentCsvModel2.setOrgName("DUMMY_ORG_NAME");
        professionalUserAccountAssignmentCsvModel2.setPbaNumber("DUMMY_PBA_NUMBER");
        professionalUserAccountAssignmentCsvModel2.setUserEmail("DUMMY@DUMMY.com");       

        assertThat(professionalUserAccountAssignmentCsvModel1, equalTo(professionalUserAccountAssignmentCsvModel2));
        assertThat(professionalUserAccountAssignmentCsvModel1.hashCode(), equalTo(professionalUserAccountAssignmentCsvModel2.hashCode()));
    }

    @Test
    public void toStringShouldReturnAStringWithAllFieldInside() throws Exception {

        ProfessionalUserAccountAssignmentCsvModel professionalUserAccountAssignmentCsvModel = new ProfessionalUserAccountAssignmentCsvModel();
        professionalUserAccountAssignmentCsvModel.setOrgName("DUMMY_ORG_NAME");
        professionalUserAccountAssignmentCsvModel.setPbaNumber("DUMMY_PBA_NUMBER");
        professionalUserAccountAssignmentCsvModel.setUserEmail("DUMMY@DUMMY.com");

        assertThat(professionalUserAccountAssignmentCsvModel.toString(), containsString("DUMMY_ORG_NAME"));
        assertThat(professionalUserAccountAssignmentCsvModel.toString(), containsString("DUMMY_PBA_NUMBER"));
        assertThat(professionalUserAccountAssignmentCsvModel.toString(), containsString("DUMMY@DUMMY.com"));
    }
}
