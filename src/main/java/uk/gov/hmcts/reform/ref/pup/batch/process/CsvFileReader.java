package uk.gov.hmcts.reform.ref.pup.batch.process;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.ref.pup.batch.exception.BatchProcessingException;

import java.io.File;
import java.util.List;

@Service
public class CsvFileReader {

    public <T> List<T> loadObjectList(Class<T> type, String filePath) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = new File(filePath);
            MappingIterator<T> readValues =
                mapper.readerFor(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            throw new BatchProcessingException("Error when creating model from CSV file " + filePath, e);
        }
    }

}
