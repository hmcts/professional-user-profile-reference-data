package uk.gov.hmcts.reform.ref.pup.exception;

public class ApplicationException extends Exception {


    public ApplicationException(String message) {
        super(message);
    }
    
    public ApplicationException(String message, Throwable e) {
        super(message, e);
    }
}
