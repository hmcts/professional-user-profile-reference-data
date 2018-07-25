package uk.gov.hmcts.reform.ref.pup.converter;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationDto;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class OrganisationConverterTest {

    
    OrganisationConverter organisationConverter = new OrganisationConverter();
    
    @Test
    public void shouldReturnNullIsObjectIsNull() throws Exception {

        OrganisationDto apply = organisationConverter.apply(null);

        assertThat(apply, equalTo(null));
    }

    @Test
    public void dataShouldMatch() throws Exception {

        UUID randomUuid = UUID.randomUUID();
        Organisation organisation = new Organisation();
        organisation.setUuid(randomUuid);
        organisation.setName("DUMMY_NAME");
        OrganisationDto organisationDto = organisationConverter.apply(organisation);

        assertThat(organisationDto.getName(), equalTo("DUMMY_NAME"));
        assertThat(organisationDto.getUuid(), equalTo(randomUuid));
    }
}
