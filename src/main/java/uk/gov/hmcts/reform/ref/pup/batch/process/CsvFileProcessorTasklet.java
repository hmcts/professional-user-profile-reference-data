package uk.gov.hmcts.reform.ref.pup.batch.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class CsvFileProcessorTasklet implements Tasklet {

    private ProfessionalUserAccountAssignmentCsvProcessor professionalUserAccountAssignmentCsvProcessor;

    public CsvFileProcessorTasklet(ProfessionalUserAccountAssignmentCsvProcessor professionalUserAccountAssignmentCsvProcessor) {
        this.professionalUserAccountAssignmentCsvProcessor = professionalUserAccountAssignmentCsvProcessor;
    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        log.info("STARTING CsvFileProcessorTasklet");
        professionalUserAccountAssignmentCsvProcessor.processCsvFiles();
        log.info("FINISHED CsvFileProcessorTasklet.");
        return RepeatStatus.FINISHED;
    }

}
