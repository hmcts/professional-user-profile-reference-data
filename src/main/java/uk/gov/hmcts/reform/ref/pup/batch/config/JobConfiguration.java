package uk.gov.hmcts.reform.ref.pup.batch.config;

import uk.gov.hmcts.reform.ref.pup.batch.model.ProfessionalUserAccountAssignmentCsvModel;
import uk.gov.hmcts.reform.ref.pup.batch.process.ProfessionalUserAccountAssignmentCsvProcessor;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUser;

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

import lombok.extern.slf4j.Slf4j;

import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    @Bean
    public MultiResourceItemReader<ProfessionalUserAccountAssignmentCsvModel> mutiCsvPupaaReader() {
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

        Resource[] resources = list.toArray(new Resource[0]);

        MultiResourceItemReader<ProfessionalUserAccountAssignmentCsvModel> reader = new MultiResourceItemReader<>();

        reader.setResources(resources);
        reader.setDelegate(singleCsvPupaaReader());
        return reader;
    }

    @Bean
    public FlatFileItemReader<ProfessionalUserAccountAssignmentCsvModel> singleCsvPupaaReader() {

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("orgName", "pbaNumber", "userEmail");
        
        BeanWrapperFieldSetMapper<ProfessionalUserAccountAssignmentCsvModel> fieldSetMapper = new BeanWrapperFieldSetMapper<ProfessionalUserAccountAssignmentCsvModel>();
        fieldSetMapper.setTargetType(ProfessionalUserAccountAssignmentCsvModel.class);
        
        
        DefaultLineMapper<ProfessionalUserAccountAssignmentCsvModel> defaultLineMapper = new DefaultLineMapper<ProfessionalUserAccountAssignmentCsvModel>();
        defaultLineMapper.setLineTokenizer(tokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        
        FlatFileItemReader<ProfessionalUserAccountAssignmentCsvModel> reader = new FlatFileItemReader<>();
        reader.setLineMapper(defaultLineMapper);
        
        return reader;
    }


    @Bean
    ItemProcessor<ProfessionalUserAccountAssignmentCsvModel, ProfessionalUser> professionalUserAccountAssignmentCsvProcessor() {
        return new ProfessionalUserAccountAssignmentCsvProcessor();
    }


    @Bean
    protected Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
            .transactionManager(transactionManager)
            .<ProfessionalUserAccountAssignmentCsvModel, ProfessionalUser>chunk(1)
            .reader(mutiCsvPupaaReader())
            .processor(professionalUserAccountAssignmentCsvProcessor())
            .exceptionHandler(exceptionHandler())// move to reject folder (add a error log file too i guess)
            .build();
    }

    @Bean
    protected ExceptionHandler exceptionHandler() {
        return new ExceptionHandler() {
            @Override
            public void handleException(RepeatContext context, Throwable throwable) throws Throwable {
                log.error(throwable.getMessage());
            }
        };
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
