package uk.gov.hmcts.reform.ref.pup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.service.notify.NotificationClient;

@Configuration
public class GovNotifyConfig {


    @Value("${gov.notify.key}")
    private String apiKey;

    @Bean
    NotificationClient notificationClient() {
        System.out.println(apiKey);
        return new NotificationClient(apiKey);
    }

}
