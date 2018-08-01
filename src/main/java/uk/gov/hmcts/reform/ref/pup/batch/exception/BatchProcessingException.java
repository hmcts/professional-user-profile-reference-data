package uk.gov.hmcts.reform.ref.pup.batch.exception;

public class BatchProcessingException extends RuntimeException {

    public BatchProcessingException(String message, Throwable e) {
        super(message, e);
    }

}
