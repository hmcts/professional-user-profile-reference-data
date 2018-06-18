package uk.gov.hmcts.reform.ref.pup.exception;

public class FileStorageException extends RuntimeException {
    public FileStorageException(Exception e) {
        super(e);
    }
}
