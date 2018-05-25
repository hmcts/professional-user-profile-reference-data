package uk.gov.hmcts.reform.pup.actuate.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConditionalOnProperty("toggle.includes2shealth")
public class IdamServiceHealth implements HealthIndicator {

    private final WebChecker idamServiceWebChecker;

    @Autowired
    public IdamServiceHealth(@Value("${reform.s2s.api.baseUrl}") String idamService) {
        idamServiceWebChecker = new WebChecker("s2s", idamService, new RestTemplate());
    }

    @Override
    public Health health() {
        return idamServiceWebChecker.health();
    }
}
