package it.progetto.energy.service;

import it.progetto.energy.model.CsvImportedDomain;
import org.springframework.web.multipart.MultipartFile;

public interface CsvService {

    CsvImportedDomain uploadProvinceAndComuniFromCSV(MultipartFile province, MultipartFile comuni);

    CsvImportedDomain uploadProvinceFromCSV(MultipartFile province);

    CsvImportedDomain uploadComuniFromCSV(MultipartFile comuni);

}
