package uk.gov.hmcts.reform.ref.pup.batch.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ref.pup.batch.model.ProfessionalUserAccountAssignmentCsvModel;
import uk.gov.hmcts.reform.ref.pup.domain.Organisation;
import uk.gov.hmcts.reform.ref.pup.domain.PaymentAccount;
import uk.gov.hmcts.reform.ref.pup.dto.OrganisationCreation;
import uk.gov.hmcts.reform.ref.pup.dto.PaymentAccountCreation;
import uk.gov.hmcts.reform.ref.pup.dto.ProfessionalUserCreation;
import uk.gov.hmcts.reform.ref.pup.service.OrganisationService;
import uk.gov.hmcts.reform.ref.pup.service.PaymentAccountService;
import uk.gov.hmcts.reform.ref.pup.service.ProfessionalUserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProfessionalUserAccountAssignmentCsvProcessor  {

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private PaymentAccountService paymentAccountService;

    @Autowired
    private ProfessionalUserService professionalUserService;

    @Autowired
    private CsvFileFetcher csvFileFetcher;

    @Autowired
    private CsvFilesReader csvFilesReader;

    public List<ProfessionalUserAccountAssignmentCsvModel> processCsvFiles() {

        List<String> files = csvFileFetcher.fetchCsvFiles();

        log.info(String.format("Found %d files", files.size()));

        if (files != null && files.size() > 0) {

            List<List<ProfessionalUserAccountAssignmentCsvModel>> listOfModels = csvFilesReader.readFiles(files);

            log.info(String.format("Successfully read %d files", files.size()));

            return listOfModels.stream().flatMap(List::stream).map(model -> {

                final String orgName = model.getOrgName();
                final String pbaNumber = model.getPbaNumber();
                final String userEmail = model.getUserEmail();

                OrganisationCreation organisationRequest = new OrganisationCreation();
                organisationRequest.setName(orgName);
                Organisation organisation = organisationService.findOrCreate(organisationRequest);

                ProfessionalUserCreation professionalUserRequest = new ProfessionalUserCreation();
                professionalUserRequest.setUserId(userEmail);
                professionalUserRequest.setEmail(userEmail);
                professionalUserService.findOrCreate(professionalUserRequest);

                PaymentAccountCreation paymentAccountRequest = new PaymentAccountCreation();
                paymentAccountRequest.setOrganisationId(organisation.getUuid());
                paymentAccountRequest.setPbaNumber(pbaNumber);
                PaymentAccount paymentAccount = paymentAccountService.findOrCreate(paymentAccountRequest);

                professionalUserService.assignPaymentAccount(professionalUserRequest.getUserId(), paymentAccount.getUuid());

                return model;

            }).collect(Collectors.toList());

        } else {
            return null;
        }

    }
}
