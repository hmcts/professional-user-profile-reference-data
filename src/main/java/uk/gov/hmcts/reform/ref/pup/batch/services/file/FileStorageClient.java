package uk.gov.hmcts.reform.ref.pup.batch.services.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.UUID;

public interface FileStorageClient {

    void uploadFile(UUID uuid, MultipartFile file);

    void streamFileContent(UUID uuid, OutputStream outputStream);

    void deleteFile(UUID uuid);

}
