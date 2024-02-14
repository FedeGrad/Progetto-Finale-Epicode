package it.progetto.energy.business;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class CsvExtractor {

    public <T> List<T> extractDataFromFileToListCsv(String path, Class<T> type) throws IOException {
        File file = new ClassPathResource(path).getFile();
        List<T> csvList = extractPojoListFromFile(file, type);
        log.info("List of {} size: {}", type.getSimpleName(), csvList.size());

        return csvList;
    }

    private <T> List<T> extractPojoListFromFile(File file, Class<T> type) throws IOException {
        char separator = detectWordSeparator(file);

        CsvSchema csvSchema =
                CsvSchema.emptySchema().withColumnSeparator(separator).withHeader();

        try (MappingIterator<T> valueTabMapped = new CsvMapper()
                .readerWithTypedSchemaFor(type)
                .with(csvSchema)
                .readValues(file)) {

            return new ArrayList<>(valueTabMapped.readAll());
        } catch (IOException e) {
            throw new IOException("SOMETING WRONG: " + e.getMessage());
        }
    }

    private char detectWordSeparator(File file) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()));
        String[] split = content.split("");
        if(Arrays.asList(split).contains(";")){
            return ';';
        } else if(Arrays.asList(split).contains(",")) {
            return ',';
        } else {
            log.info("NO SEPARATOR DETECTED");
            return ' ';
        }
    }

}
