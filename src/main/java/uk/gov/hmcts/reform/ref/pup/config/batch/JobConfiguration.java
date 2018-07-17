package uk.gov.hmcts.reform.ref.pup.config.batch;

import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUserAccountAssignment;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUserAccountAssignmentCsvDTO;
import uk.gov.hmcts.reform.ref.pup.services.batch.ProfessionalUserAccountAssignmentCsvProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnProperty("toggle.uploadCSV")
public class JobConfiguration {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CloudBlobContainer cloudBlobContainer;

    private static final Logger log = LoggerFactory.getLogger(JobConfiguration.class);

    @Bean
    public MultiResourceItemReader<ProfessionalUserAccountAssignmentCsvDTO> mutiCsvPupaaReader() {
        List<Resource> list = new ArrayList<>();

        for (ListBlobItem blobItem : cloudBlobContainer.listBlobs()) {
            if (blobItem.getUri().toString().endsWith(".csv")) {
                try {
                    list.add(new UrlResource(blobItem.getUri()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                log.info("======================================================");
                log.info("======================================================");
                log.info("NOT CSV" + blobItem.getUri().toString());
                log.info("======================================================");
                log.info("======================================================");
            }
        }

        log.info("======================================================");
        log.info("======================================================");
        log.info("======================================================");
        list.forEach(resource -> log.info("ALECTRONIC" + resource.getFilename()));
        log.info("======================================================");
        log.info("======================================================");
        log.info("======================================================");
        log.info("======================================================");

        Resource[] resources = list.toArray(new Resource[0]);

        MultiResourceItemReader<ProfessionalUserAccountAssignmentCsvDTO> reader = new MultiResourceItemReader<>();

        reader.setResources(resources);
        reader.setDelegate(singleCsvPupaaReader());
        return reader;
    }

    @Bean
    public FlatFileItemReader<ProfessionalUserAccountAssignmentCsvDTO> singleCsvPupaaReader() {

        FlatFileItemReader<ProfessionalUserAccountAssignmentCsvDTO> reader = new FlatFileItemReader<>();
        reader.setLineMapper(new DefaultLineMapper<ProfessionalUserAccountAssignmentCsvDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("orgName", "pbaNumber", "userEmail");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<ProfessionalUserAccountAssignmentCsvDTO>() {{
                setTargetType(ProfessionalUserAccountAssignmentCsvDTO.class);
            }});
        }});
        return reader;
    }


    @Bean
    ItemProcessor<ProfessionalUserAccountAssignmentCsvDTO, ProfessionalUserAccountAssignment> professionalUserAccountAssignmentCsvProcessor() {
        return new ProfessionalUserAccountAssignmentCsvProcessor();
    }


    @Bean
    protected Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
            .transactionManager(transactionManager)
            .<ProfessionalUserAccountAssignmentCsvDTO, ProfessionalUserAccountAssignment>chunk(1)
//            get list of uri's
//            one by one do the next steps
            .reader(mutiCsvPupaaReader())
            .processor(professionalUserAccountAssignmentCsvProcessor())
            .exceptionHandler(new ExceptionHandler() {
                @Override
                public void handleException(RepeatContext context, Throwable throwable) throws Throwable {
                    log.info("======================================================");
                    log.info("======================================================");
                    log.error("SOMETHING WENT WRONG");
                    log.error(throwable.getMessage());
//                    send an email and move to a reject folder
                    log.info("======================================================");
                    log.info("======================================================");

//                    CloudAppendBlob blob = cloudBlobContainer.cr("error.log");
//                    blob.appendText(throwable.getMessage());
                }
            })// move to reject folder (add a error log file too i guess)
            .build();
    }

    @Bean
    Job csvFileToDatabaseJob() {
        return jobBuilderFactory
            .get("csvFileToDatabaseJob")
            .incrementer(new RunIdIncrementer())
            .flow(csvFileToDatabaseStep())
            .end()
            .build();
    }

}
