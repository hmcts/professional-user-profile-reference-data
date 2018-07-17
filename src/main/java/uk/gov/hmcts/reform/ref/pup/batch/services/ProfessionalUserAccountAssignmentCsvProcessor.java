package uk.gov.hmcts.reform.ref.pup.batch.services;

import uk.gov.hmcts.reform.ref.pup.batch.domain.ProfessionalUserAccountAssignmentCsvDTO;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUserAccountAssignment;
import uk.gov.hmcts.reform.ref.pup.services.domain.ProfessionalUserAccountAssignmentService;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfessionalUserAccountAssignmentCsvProcessor implements ItemProcessor<ProfessionalUserAccountAssignmentCsvDTO, ProfessionalUserAccountAssignment> {

    @Autowired
    private ProfessionalUserAccountAssignmentService professionalUserAccountAssignmentService;

    @Override
    public ProfessionalUserAccountAssignment process(ProfessionalUserAccountAssignmentCsvDTO professionalUserAccountAssignmentCsvDTO) throws Exception {
        return professionalUserAccountAssignmentService.createProfessionalUserAccountWithDTO(professionalUserAccountAssignmentCsvDTO);
    }
}
