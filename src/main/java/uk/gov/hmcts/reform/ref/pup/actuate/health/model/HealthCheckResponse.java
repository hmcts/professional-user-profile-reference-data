package uk.gov.hmcts.reform.ref.pup.actuate.health.model;

public class HealthCheckResponse {

    public HealthCheckResponse(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
