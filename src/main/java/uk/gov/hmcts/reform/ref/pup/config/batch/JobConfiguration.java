package uk.gov.hmcts.reform.ref.pup.config.batch;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUserAccountAssignment;
import uk.gov.hmcts.reform.ref.pup.domain.ProfessionalUserAccountAssignmentCsvDTO;
import uk.gov.hmcts.reform.ref.pup.services.batch.ProfessionalUserAccountAssignmentCsvProcessor;

@Configuration
public class JobConfiguration {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<ProfessionalUserAccountAssignmentCsvDTO> csvPupaaReader(){
        FlatFileItemReader<ProfessionalUserAccountAssignmentCsvDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("PBAdata.csv"));
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
    ItemProcessor<ProfessionalUserAccountAssignmentCsvDTO, ProfessionalUserAccountAssignment> csvPupaaProcessor() {
        return new ProfessionalUserAccountAssignmentCsvProcessor();
    }

//    @Bean
//    ItemProcessor<ProfessionalUserAccountAssignment, ProfessionalUserAccountAssignment> savePupaaProcessor() {
//        return new ProfessionalUserAccountAssignmentSaveProcessor();
//    }


    @Bean
    protected Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
            .transactionManager(transactionManager)
            .<ProfessionalUserAccountAssignmentCsvDTO, ProfessionalUserAccountAssignment>chunk(1)
            .reader(csvPupaaReader())
            .processor(csvPupaaProcessor())
//            .processor(savePupaaProcessor())
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
