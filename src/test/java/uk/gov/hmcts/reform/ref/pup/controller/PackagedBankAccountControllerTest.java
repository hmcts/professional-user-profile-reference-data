package uk.gov.hmcts.reform.ref.pup.controller;

import uk.gov.hmcts.reform.ref.pup.adaptor.PaymentAccountServiceAdaptor;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountCreation;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountDto;

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
public class PackagedBankAccountControllerTest {

    @Mock
    protected PaymentAccountServiceAdaptor paymentAccountServiceAdaptor;
    
    @InjectMocks
    protected PackagedBankAccountController packagedBankAccountController;
    
    @Captor
    ArgumentCaptor<PaymentAccountCreation> paymentAccountCaptor;
    
    @Captor
    ArgumentCaptor<String> paymentAccountIdCaptor;
    
    @Captor
    ArgumentCaptor<UUID> organisationIdCaptor;
    
    private MockMvc mvc;

    private PaymentAccountDto firstTestPaymentAccount;
    private String firstTestPaymentAccountCreationJson;
    
    @Before
    public void setUp() throws Exception {
        
        mvc = MockMvcBuilders.standaloneSetup(packagedBankAccountController).build();
        firstTestPaymentAccount = createFakePaymentAccountDto();
        firstTestPaymentAccountCreationJson = "{\"uuId\":\"c6c561cd-8f68-474e-89d3-13fece9b66f7\",\"pbaNumber\":\"DUMMY\",\"organisationId\":\"c6c561cd-8f68-474e-89d3-13fece9b66f8\"}";
        
    }

    private PaymentAccountDto createFakePaymentAccountDto() {
        return PaymentAccountDto.builder()
                        .pbaNumber("DUMMY")
                        .build();
    }

    
    @Test
    public void createPaymentAccountShouldCallCreateFormPaymentAccountService() throws Exception {
        
        when(paymentAccountServiceAdaptor.create(any())).thenReturn(firstTestPaymentAccount);
        
        mvc.perform(post("/pup/pba").with(user("user"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(firstTestPaymentAccountCreationJson))
            .andExpect(status().isOk())
            .andDo(print());
        
        verify(paymentAccountServiceAdaptor, only()).create(paymentAccountCaptor.capture());
        assertThat(paymentAccountCaptor.getValue().getPbaNumber(), equalTo("DUMMY"));
        assertThat(paymentAccountCaptor.getValue().getOrganisationId().toString(), equalTo("c6c561cd-8f68-474e-89d3-13fece9b66f8"));
        
    }
    
    @Test
    public void getPaymentAccountShouldReturnThePaymentAccount() throws Exception {

        when(paymentAccountServiceAdaptor.retrieve("1")).thenReturn(Optional.of(firstTestPaymentAccount));

        mvc.perform(get("/pup/pba/1").with(user("user")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("pbaNumber", is("DUMMY")))
            .andDo(print());
    }

    @Test
    public void getPaymentAccountShouldReturnNotFoundIfTheServiceReturnEmpty() throws Exception {
        
        when(paymentAccountServiceAdaptor.retrieve("1")).thenReturn(Optional.empty());   
        
        mvc.perform(get("/pup/pba/1").with(user("user")))
            .andExpect(status().isNotFound())
            .andDo(print());
    }
    
    @Test
    public void deletePaymentAccountShouldCallTheDeleteMethodFormProfessionalUserService() throws Exception {
        
        mvc.perform(delete("/pup/pba/1").with(user("user")))
            .andExpect(status().isNoContent())
            .andDo(print());
        
        verify(paymentAccountServiceAdaptor, only()).delete(paymentAccountIdCaptor.capture());
        assertThat(paymentAccountIdCaptor.getValue(), equalTo("1"));
        
    }
    
}
