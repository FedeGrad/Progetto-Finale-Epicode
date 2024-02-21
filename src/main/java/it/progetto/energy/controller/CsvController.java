package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.CsvAPI;
import it.progetto.energy.dto.csv.CsvImportedOutputDTO;
import it.progetto.energy.mapper.CsvDTOMapper;
import it.progetto.energy.model.CsvImportedDomain;
import it.progetto.energy.service.impl.CsvServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;

@RestController
@RequestMapping("/csv")
@Tag(name = "Importer of data from file.csv", description = "Get Province and Comuni from csv")
@Slf4j
@RequiredArgsConstructor
public class CsvController implements CsvAPI {

    private final CsvServiceImpl csvService;
    private final CsvDTOMapper csvDTOMapper;

    @Override
    @PostMapping(value = "/province/comuni", consumes = MULTIPART_FORM_DATA)
    @ResponseStatus(HttpStatus.CREATED)
    public CsvImportedOutputDTO importProvinceAndComuniCSV(
            @RequestParam(name = "province") MultipartFile province,
            @RequestParam(name = "comuni") MultipartFile comuni) {
        CsvImportedDomain csvImportedDomain = csvService.uploadProvinceAndComuniFromCSV(province, comuni);
        return csvDTOMapper.fromCsvImportedDomainToCsvImportedOutputDTO(csvImportedDomain);
    }

    @Override
    @PostMapping(value = "/province", consumes = MULTIPART_FORM_DATA)
    @ResponseStatus(HttpStatus.CREATED)
    public CsvImportedOutputDTO importProvinceCSV(@RequestParam(name = "province") MultipartFile province) {
        CsvImportedDomain csvImportedDomain = csvService.uploadProvinceFromCSV(province);
        return csvDTOMapper.fromCsvImportedDomainToCsvImportedOutputDTO(csvImportedDomain);
    }

    @Override
    @PostMapping(value = "/comuni", consumes = MULTIPART_FORM_DATA)
    @ResponseStatus(HttpStatus.CREATED)
    public CsvImportedOutputDTO importComuniCSV(@RequestParam(name = "comuni") MultipartFile comuni) {
        CsvImportedDomain csvImportedDomain = csvService.uploadComuniFromCSV(comuni);
        return csvDTOMapper.fromCsvImportedDomainToCsvImportedOutputDTO(csvImportedDomain);
    }

}
