package uk.gov.hmcts.reform.ref.pup.batch.process;

import com.microsoft.azure.storage.file.CloudFile;
import com.microsoft.azure.storage.file.CloudFileDirectory;
import com.microsoft.azure.storage.file.CloudFileShare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ref.pup.batch.exception.BatchProcessingException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class CsvFileFetcher {

    private CloudFileShare cloudFileShare;

    private final static String JAVA_TMP_DIR = System.getProperty("java.io.tmpdir");

    public CsvFileFetcher(CloudFileShare cloudFileShare) {
        this.cloudFileShare = cloudFileShare;
    }

    public List<String> fetchCsvFiles() {

        try {

            if (cloudFileShare.exists()) {
                CloudFileDirectory rootDirectoryReference = cloudFileShare.getRootDirectoryReference();

                return StreamSupport.stream(rootDirectoryReference.listFilesAndDirectories().spliterator(), false)
                    .filter(item -> item.getUri().toString().endsWith(".csv"))
                    .map(item -> (CloudFile) item)
                    .map(file -> {
                        try {
                            String fullPath = JAVA_TMP_DIR + File.separator + file.getName();
                            Files.deleteIfExists(Paths.get(fullPath));
                            file.downloadToFile(fullPath);
                            file.delete();
                            return fullPath;
                        } catch (Exception e) {
                            throw new BatchProcessingException("Error fetching files", e);
                        }
                    })
                    .collect(Collectors.toList());
            } else {
                log.info("cloudFileShare does not exist");
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

}
