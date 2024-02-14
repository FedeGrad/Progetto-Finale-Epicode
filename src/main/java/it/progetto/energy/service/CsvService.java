package it.progetto.energy.service;

import org.springframework.web.multipart.MultipartFile;

public interface CsvService {

    String uploadProvinceAndComuniFromCSV(MultipartFile province, MultipartFile comuni);

    String uploadProvinceFromCSV(MultipartFile province);

    String uploadComuniFromCSV(MultipartFile comuni);

}
