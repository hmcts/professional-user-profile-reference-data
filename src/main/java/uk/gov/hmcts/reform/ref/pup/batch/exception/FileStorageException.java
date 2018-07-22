package uk.gov.hmcts.reform.ref.pup.batch.exception;

public class FileStorageException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public FileStorageException(Exception e) {
        super(e);
    }
}
