package uk.gov.hmcts.reform.ref.pup.converter;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserFullDetailDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProfessionalUserFullDetailConverter implements Function<ProfessionalUser, ProfessionalUserFullDetailDto> {

    private final OrganisationConverter organisationConverter;

    private final PaymentAccountConverter paymentAccountConverter;

    @Autowired
    public ProfessionalUserFullDetailConverter(OrganisationConverter organisationConverter, PaymentAccountConverter paymentAccountConverter) {

        this.organisationConverter = organisationConverter;
        this.paymentAccountConverter = paymentAccountConverter;

    }

    @Override
    public ProfessionalUserFullDetailDto apply(ProfessionalUser source) {
        if (source == null) {
            return null;
        }

        return ProfessionalUserFullDetailDto.builder()
                .userId(source.getUserId())
                .firstName(source.getFirstName())
                .surname(source.getSurname())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .organisation(organisationConverter.apply(source.getOrganisation()))
                .paymentAccounts(source.getAccountAssignments().stream()
                                            .map(paymentAccountConverter)
                                            .collect(Collectors.toList()))
                .build();

    }
}