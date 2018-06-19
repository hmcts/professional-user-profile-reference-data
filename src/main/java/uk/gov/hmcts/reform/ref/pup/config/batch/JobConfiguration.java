package uk.gov.hmcts.reform.ref.pup.config.batch;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.transaction.PlatformTransactionManager;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUserAccountAssignment;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUserAccountAssignmentCsvDTO;
import uk.gov.hmcts.reform.ref.pup.services.batch.ProfessionalUserAccountAssignmentCsvProcessor;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

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
    private CloudStorageAccount cloudStorageAccount;

    @Bean
    public FlatFileItemReader<ProfessionalUserAccountAssignmentCsvDTO> csvPupaaReader() throws URISyntaxException, StorageException, MalformedURLException {
        URI uri = cloudStorageAccount.createCloudBlobClient()
            .getContainerReference("storage")
            .getBlobReferenceFromServer("PBAdata.csv")
            .getUri();
        FlatFileItemReader<ProfessionalUserAccountAssignmentCsvDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new UrlResource(uri));
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
    protected Step csvFileToDatabaseStep() throws MalformedURLException, StorageException, URISyntaxException {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
            .transactionManager(transactionManager)
            .<ProfessionalUserAccountAssignmentCsvDTO, ProfessionalUserAccountAssignment>chunk(1)
            .reader(csvPupaaReader())
            .processor(professionalUserAccountAssignmentCsvProcessor())
//            .processor(savePupaaProcessor())
            .build();
    }

    @Bean
    Job csvFileToDatabaseJob() throws URISyntaxException, StorageException, MalformedURLException {
        return jobBuilderFactory
            .get("csvFileToDatabaseJob")
            .incrementer(new RunIdIncrementer())
            .flow(csvFileToDatabaseStep())
            .end()
            .build();
    }

}
