package uk.gov.hmcts.reform.ref.pup.services.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UploadCSVTasklet implements Tasklet {

    @Autowired
    private CSVUploadService csvUploadService;

    @Override
    @Transactional
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        csvUploadService.uploadCSV();
        return RepeatStatus.FINISHED;
    }
}
