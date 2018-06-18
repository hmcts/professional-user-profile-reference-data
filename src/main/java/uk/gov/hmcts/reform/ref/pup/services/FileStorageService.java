package uk.gov.hmcts.dm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ref.pup.services.azure.AzureBlobStorageClient;

import java.io.OutputStream;
import java.util.UUID;

@Service
public class FileStorageService {

    //@Value("#{ '${file-storage-client}' == 'azureFileStorageClient' ? azureFileStorageClient : azureBlobStorageClient}")
    @Autowired
    private AzureBlobStorageClient fileStorageClient;

//    public DocumentContentVersion uploadFile(StoredDocument storedDocument, MultipartFile file, String creatorId) {
//        UUID uuid = UUID.randomUUID();
//        fileStorageClient.uploadFile(uuid, file);
//
//        return new DocumentContentVersion(
//            uuid,
//            storedDocument,
//            file,
//            creatorId);
//    }

//    public void delete(DocumentContentVersion documentContentVersion) {
//        fileStorageClient.deleteFile(documentContentVersion.getId());
//    }

    public void streamBinary(UUID uuid, OutputStream outputStream) {
        fileStorageClient.streamFileContent(uuid, outputStream);
    }

}
