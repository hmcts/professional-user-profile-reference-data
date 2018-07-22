package uk.gov.hmcts.reform.ref.pup.batch.exception;

public class FileStorageException extends RuntimeException {
    public FileStorageException(Exception e) {
        super(e);
    }
}
