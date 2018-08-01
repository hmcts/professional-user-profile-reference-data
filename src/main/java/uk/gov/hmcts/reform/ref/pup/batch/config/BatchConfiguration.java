package uk.gov.hmcts.reform.ref.pup.batch.config;

import uk.gov.hmcts.reform.ref.pup.batch.exception.AppConfigurationException;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@EnableConfigurationProperties(BatchProperties.class)
public class BatchConfiguration {

    @Bean
    public JobRepository jobRepository(DataSource dataSource, @Qualifier("transactionManager") PlatformTransactionManager transactionManager) {

        try {
            JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
            factory.setDataSource(dataSource);
            factory.setTransactionManager(transactionManager);
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (Exception e) {
            throw new AppConfigurationException("Could not findOrCreate 'jobRepository' bean", e);
        }

    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        return simpleJobLauncher;
    }
}
