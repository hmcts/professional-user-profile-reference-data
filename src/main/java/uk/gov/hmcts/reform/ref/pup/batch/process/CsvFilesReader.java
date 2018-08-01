package uk.gov.hmcts.reform.ref.pup.batch.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ref.pup.batch.model.ProfessionalUserAccountAssignmentCsvModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvFilesReader {

    @Autowired
    private CsvFileReader csvFileReader;

    List<List<ProfessionalUserAccountAssignmentCsvModel>> readFiles(List<String> filePaths) {
        return filePaths
            .stream()
            .map(filePath -> csvFileReader.loadObjectList(ProfessionalUserAccountAssignmentCsvModel.class, filePath))
            .collect(Collectors.toList());
    }

}
