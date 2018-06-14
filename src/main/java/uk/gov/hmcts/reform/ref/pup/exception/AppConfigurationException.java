package uk.gov.hmcts.reform.ref.pup.exception;

public class AppConfigurationException extends RuntimeException {

    public AppConfigurationException(String message, Throwable e) {
        super(message, e);
    }
}
