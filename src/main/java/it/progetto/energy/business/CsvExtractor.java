package it.progetto.energy.business;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import it.progetto.energy.exception.FileException;
import it.progetto.energy.mapper.UtilsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;

@Component
@Slf4j
@RequiredArgsConstructor
public class CsvExtractor {

    private final UtilsMapper utilsMapper;

    public <T> List<T> extractDataFromMultipartFileToListCsv(MultipartFile multipartFile, Class<T> type) {
        File file = utilsMapper.fromMultipartFileToFile(multipartFile);
        List<T> csvList = extractPojoListFromFile(file, type);
        log.info("List of {} size: {}", type.getSimpleName(), csvList.size());

        return csvList;
    }

    public <T> List<T> extractDataFromFileToListCsv(String path, Class<T> type) {
        try {
            File file = new ClassPathResource(path).getFile();

            List<T> csvList = extractPojoListFromFile(file, type);
            log.info("List of {} size: {}", type.getSimpleName(), csvList.size());

            return csvList;
        } catch (IOException e) {
            throw new FileException(ERROR_ONE);
        }
    }

    private <T> List<T> extractPojoListFromFile(File file, Class<T> type) {
        char separator = detectWordSeparator(file);

        CsvSchema csvSchema =
                CsvSchema.emptySchema().withColumnSeparator(separator).withHeader();

        try (MappingIterator<T> valueTabMapped = new CsvMapper()
                .readerWithTypedSchemaFor(type)
                .with(csvSchema)
                .readValues(file)) {

            return new ArrayList<>(valueTabMapped.readAll());
        } catch (IOException e) {
            throw new FileException(ERROR_ONE); //todo change
        }
    }

    private char detectWordSeparator(File file)  {
        try {
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
        } catch (IOException e) {
            throw new FileException(ERROR_ONE); //todo change
        }
    }

}