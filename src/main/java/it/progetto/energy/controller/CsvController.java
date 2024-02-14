package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.CsvAPI;
import it.progetto.energy.service.impl.CsvServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/csv")
@Tag(name = "Importer of data from file.csv", description = "Get Province and Comuni from csv")
@Slf4j
@RequiredArgsConstructor
public class CsvController implements CsvAPI {

    private final CsvServiceImpl csvService;

    @Override
    @PostMapping("/province/comuni")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> importProvinceAndComuniCSV(MultipartFile province, MultipartFile comuni) {
        String result = csvService.uploadProvinceAndComuniFromCSV(province, comuni);
        return ResponseEntity.ok().body(result);
    }

    @Override
    @PostMapping("/province")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> importProvinceCSV(MultipartFile province) {
        String result = csvService.uploadProvinceFromCSV(province);
        return ResponseEntity.ok().body(result);
    }

    @Override
    @PostMapping("/comuni")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> importComuniCSV(MultipartFile comuni) {
        String result = csvService.uploadComuniFromCSV(comuni);
        return ResponseEntity.ok().body(result);
    }

}
