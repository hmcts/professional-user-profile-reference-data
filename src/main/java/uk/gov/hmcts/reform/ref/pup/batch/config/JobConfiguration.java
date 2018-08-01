package uk.gov.hmcts.reform.ref.pup.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.ref.pup.batch.process.CsvFileProcessorTasklet;
import uk.gov.hmcts.reform.ref.pup.batch.process.ProfessionalUserAccountAssignmentCsvProcessor;

@Slf4j
@Configuration
@ConditionalOnProperty("toggle.uploadCSV")
public class JobConfiguration {

//    @Autowired
//    private PlatformTransactionManager transactionManager;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//    @Autowired
//    private CloudFileShare cloudFileShare;

    @Autowired
    private ProfessionalUserAccountAssignmentCsvProcessor professionalUserAccountAssignmentCsvProcessor;



//    @Bean
//    public MultiResourceItemReader<ProfessionalUserAccountAssignmentCsvModel> multiResourceAzureCsvReader() {
//
//        MultiResourceItemReader<ProfessionalUserAccountAssignmentCsvModel> reader = null;
//
//        try {
//            if (cloudFileShare.exists()) {
//                CloudFileDirectory rootDirectoryReference = cloudFileShare.getRootDirectoryReference();
//
//                Resource[] resources = StreamSupport.stream(rootDirectoryReference.listFilesAndDirectories().spliterator(), false)
//                    .filter( item -> item.getUri().toString().endsWith(".csv") )
//                    .map( item -> (CloudFile)item )
//                    .map( file -> {
//                        try {
//                            String fullPath = JAVA_TMP_DIR + File.separator + file.getName();
//                            Files.delete(Paths.get(fullPath));
//                            file.downloadToFile(fullPath);
//                            file.delete();
//                            return new FileSystemResource(fullPath);
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                    })
//                    .toArray(size -> new Resource[size]);
//
//                reader = new MultiResourceItemReader<>();
//
//                reader.setResources(resources);
//                reader.setDelegate(singleCsvReader());
//            }
//        } catch (StorageException | URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//
//        return reader;
//    }

//    @Bean
//    public FlatFileItemReader<ProfessionalUserAccountAssignmentCsvModel> singleCsvReader() {
//
//        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//        tokenizer.setNames("orgName", "pbaNumber", "userEmail");
//
//        BeanWrapperFieldSetMapper<ProfessionalUserAccountAssignmentCsvModel> fieldSetMapper = new BeanWrapperFieldSetMapper<ProfessionalUserAccountAssignmentCsvModel>();
//        fieldSetMapper.setTargetType(ProfessionalUserAccountAssignmentCsvModel.class);
//
//
//        DefaultLineMapper<ProfessionalUserAccountAssignmentCsvModel> defaultLineMapper = new DefaultLineMapper<ProfessionalUserAccountAssignmentCsvModel>();
//        defaultLineMapper.setLineTokenizer(tokenizer);
//        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
//
//        FlatFileItemReader<ProfessionalUserAccountAssignmentCsvModel> reader = new FlatFileItemReader<>();
//        reader.setLineMapper(defaultLineMapper);
//
//        return reader;
//    }

//
//    @Bean
//    ItemProcessor<ProfessionalUserAccountAssignmentCsvModel, ProfessionalUser> professionalUserAccountAssignmentCsvProcessor() {
//        return new ProfessionalUserAccountAssignmentCsvProcessor();
//    }


//    @Bean
//    protected Step csvFileToDatabaseStep() {
//        return stepBuilderFactory.get("csvFileToDatabaseStep")
//            .transactionManager(transactionManager)
//            .<ProfessionalUserAccountAssignmentCsvModel, ProfessionalUser>chunk(1)
//            .reader(multiResourceAzureCsvReader())
//            .processor(professionalUserAccountAssignmentCsvProcessor())
//            .exceptionHandler(exceptionHandler())// move to reject folder (add a error log file too i guess)
//            .build();
//    }

    @Bean
    CsvFileProcessorTasklet csvFileProcessor() {
        return new CsvFileProcessorTasklet(professionalUserAccountAssignmentCsvProcessor);
    }

    @Bean
    protected Step fetchFiles() {
        return stepBuilderFactory
            .get("csvFileToDatabaseJob")
            .tasklet(csvFileProcessor())
            .build();
    }
//
//    @Bean
//    protected ExceptionHandler exceptionHandler() {
//        return new ExceptionHandler() {
//            @Override
//            public void handleException(RepeatContext context, Throwable throwable) throws Throwable {
//                log.error("ERROR PROCESSING CSV FILES", throwable.getMessage());
//            }
//        };
//    }

    @Bean
    Job csvFileToDatabaseJob() {
        return jobBuilderFactory
            .get("csvFileToDatabaseJob")
            .incrementer(new RunIdIncrementer())
            .start(fetchFiles())
            .build();
    }

}
