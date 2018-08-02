package uk.gov.hmcts.reform.ref.pup.controller;

import uk.gov.hmcts.reform.ref.pup.dto.AddressCreation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationCreation;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationDto;
import uk.gov.hmcts.reform.ref.pup.service.adaptor.OrganisationServiceAdaptor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
public class OrganisationControllerTest {

    @Mock
    protected OrganisationServiceAdaptor organisationService;

    @InjectMocks
    protected OrganisationController organisationController;

    @Captor
    ArgumentCaptor<UUID> organisationIdCaptor;

    @Captor
    ArgumentCaptor<OrganisationCreation> organisationCaptor;

    @Captor
    ArgumentCaptor<AddressCreation> addressCaptor;

    private MockMvc mvc;

    private OrganisationDto firstTestOrganisation;
    private String firstTestOrganisationJson;
    private String firstTestAddressJson;

    @Before
    public void setUp() throws Exception {

        mvc = MockMvcBuilders.standaloneSetup(organisationController).build();

        firstTestOrganisation = createFakeOrganisationDto();
        firstTestOrganisationJson = "{\"name\":\"Solicitor Ltd\"}";
        firstTestAddressJson = "{\"addressLine1\":\"address 1\",\"addressLine2\":\"address 2\",\"addressLine3\":\"address 3\",\"city\":\"london\",\"county\":\"UK\",\"country\":\"UK\",\"postcode\":\"N112AS\"}";

    }

    private OrganisationDto createFakeOrganisationDto() {
        return OrganisationDto.builder()
                        .name("DUMMY")
                        .build();

    }

    @Test
    public void createOrganisationShouldCallCreateFormOrganisationService() throws Exception {

        when(organisationService.create(any())).thenReturn(firstTestOrganisation);

        mvc.perform(post("/pup/organisations").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestOrganisationJson))
            .andExpect(status().isOk())
            .andDo(print());

        verify(organisationService, only()).create(organisationCaptor.capture());
        assertThat(organisationCaptor.getValue().getName(), equalTo("Solicitor Ltd"));

    }

    @Test
    public void getOrganisationShouldReturnTheUser() throws Exception {

        when(organisationService.retrieve(any())).thenReturn(Optional.of(firstTestOrganisation));

        mvc.perform(get("/pup/organisations/c6c561cd-8f68-474e-89d3-13fece9b66f8").with(user("user")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("name", is("DUMMY")))
            .andDo(print());
    }

    @Test
    public void getOrganisationShouldReturnNotFoundIfTheServiceReturnEmpty() throws Exception {

        when(organisationService.retrieve(any())).thenReturn(Optional.empty());

        mvc.perform(get("/pup/organisations/c6c561cd-8f68-474e-89d3-13fece9b66f8").with(user("user")))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    public void deleteOrganisationShouldCallTheDeleteFormOrganisationService() throws Exception {

        mvc.perform(delete("/pup/organisations/c6c561cd-8f68-474e-89d3-13fece9b66f8").with(user("user")))
            .andExpect(status().isNoContent())
            .andDo(print());

        verify(organisationService, only()).delete(organisationIdCaptor.capture());
        assertThat(organisationIdCaptor.getValue(), equalTo(UUID.fromString("c6c561cd-8f68-474e-89d3-13fece9b66f8")));

    }

    @Test
    public void addOrganisationAddressShouldCallAddAddressFormOrganisationService() throws Exception {

        when(organisationService.addAddress(any(), any())).thenReturn(firstTestOrganisation);

        mvc.perform(post("/pup/organisations/{uuid}/address", "c6c561cd-8f68-474e-89d3-13fece9b66f8").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestAddressJson))
            .andExpect(status().isOk())
            .andDo(print());

        verify(organisationService, only()).addAddress(any(), addressCaptor.capture());
        assertThat(addressCaptor.getValue().getAddressLine1(), equalTo("address 1"));

    }

}
