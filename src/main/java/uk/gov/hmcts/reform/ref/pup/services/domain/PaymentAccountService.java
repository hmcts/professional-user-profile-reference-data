package uk.gov.hmcts.reform.ref.pup.services.domain;

import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.repository.PaymentAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentAccountService {

    private final PaymentAccountRepository paymentAccountRepository;

    private final OrganisationService organisationService;

    @Autowired
    public PaymentAccountService(PaymentAccountRepository paymentAccountRepository,
                                 OrganisationService organisationService) {
        this.paymentAccountRepository = paymentAccountRepository;
        this.organisationService = organisationService;
    }

    public PaymentAccount createPaymentAccountWithPbaNumberAndOrgName(String pbaNumber, String orgName) {
        final Organisation organisation = organisationService
            .createOrganisationWithJustName(orgName);

        List<PaymentAccount> paymentAccounts = paymentAccountRepository
            .findByPbaNumberAndOrganisation(pbaNumber,organisation);

        return paymentAccounts.isEmpty() ?
            createPaymentAccount(pbaNumber,organisation)
            : paymentAccounts.get(0);
    }

    public PaymentAccount createPaymentAccount(String pbaNumber, Organisation organisation) {
        return this.paymentAccountRepository.save(
            PaymentAccount
                .builder()
                .pbaNumber(pbaNumber)
//                .pbaType(PaymentAccountType.builder().name("UNKNOWN").build())
                .organisation(organisation)
                .build()
        );
    }
}
