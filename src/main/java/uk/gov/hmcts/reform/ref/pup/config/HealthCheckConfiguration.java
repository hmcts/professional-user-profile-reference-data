package uk.gov.hmcts.reform.ref.pup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.system.DiskSpaceHealthIndicatorProperties;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HealthCheckConfiguration {

    @Bean
    DiskSpaceHealthIndicator diskSpaceHealthIndicator(@Value("${management.health.disk.threshold}") long threshold) {
        DiskSpaceHealthIndicatorProperties diskSpaceHealthIndicatorProperties =
            new DiskSpaceHealthIndicatorProperties();
        diskSpaceHealthIndicatorProperties.setThreshold(threshold);
        return new DiskSpaceHealthIndicator(
            diskSpaceHealthIndicatorProperties.getPath(),
            diskSpaceHealthIndicatorProperties.getThreshold()
        );
    }

}
