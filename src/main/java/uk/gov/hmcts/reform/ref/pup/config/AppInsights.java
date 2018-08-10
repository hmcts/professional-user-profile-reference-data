package uk.gov.hmcts.reform.ref.pup.config;

import uk.gov.hmcts.reform.logging.appinsights.AbstractAppInsights;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.microsoft.applicationinsights.TelemetryClient;

@Component
@ConditionalOnProperty("azure.app_insights_key")
public class AppInsights extends AbstractAppInsights {

    private static final Logger LOG = LoggerFactory.getLogger(AppInsights.class);

    @Autowired
    public AppInsights(TelemetryClient client) {
        super(client);
        LOG.info("Building AppInsights");
    }
}
