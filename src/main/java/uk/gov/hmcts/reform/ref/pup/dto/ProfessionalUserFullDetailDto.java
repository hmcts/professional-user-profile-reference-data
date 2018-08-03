package uk.gov.hmcts.reform.ref.pup.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class ProfessionalUserFullDetailDto {

    private String userId;

    private String firstName;

    private String surname;

    private String email;

    private String phoneNumber;

    private OrganisationDto organisation;

    private AddressDto address;

    private List<PaymentAccountDto> paymentAccounts;

}
