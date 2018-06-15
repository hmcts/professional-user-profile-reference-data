package uk.gov.hmcts.reform.ref.pup.services.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.hmcts.reform.ref.pup.domain.*;
import uk.gov.hmcts.reform.ref.pup.repository.ProfessionalUserAccountAssignmentRepository;

import javax.transaction.Transactional;

public class ProfessionalUserAccountAssignmentSaveProcessor implements ItemProcessor<ProfessionalUserAccountAssignment, ProfessionalUserAccountAssignment> {

    private static final Logger log = LoggerFactory.getLogger(ProfessionalUserAccountAssignmentSaveProcessor.class);

    @Autowired
    private ProfessionalUserAccountAssignmentRepository professionalUserAccountAssignmentRepository;

    @Override
    @Transactional
    public ProfessionalUserAccountAssignment process(ProfessionalUserAccountAssignment professionalUserAccountAssignment) throws Exception {
        return professionalUserAccountAssignmentRepository.save(professionalUserAccountAssignment);
    }
}
