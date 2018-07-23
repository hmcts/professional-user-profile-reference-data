package uk.gov.hmcts.reform.ref.pup.actuate.health.model;

public class HealthCheckResponse {

    private final String status;
    
    public HealthCheckResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
