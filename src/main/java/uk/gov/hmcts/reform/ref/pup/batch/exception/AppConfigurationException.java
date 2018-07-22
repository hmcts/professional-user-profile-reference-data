package uk.gov.hmcts.reform.ref.pup.batch.exception;

public class AppConfigurationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AppConfigurationException(String message, Throwable e) {
        super(message, e);
    }
}
