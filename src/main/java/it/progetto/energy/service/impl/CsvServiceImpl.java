package it.progetto.energy.service.impl;

import it.progetto.energy.service.CsvService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvServiceImpl implements CsvService {

    @Override
    public String uploadProvinceAndComuniFromCSV(MultipartFile province, MultipartFile comuni) {
        return null;
    }

    @Override
    public String uploadProvinceFromCSV(MultipartFile province) {
        return null;
    }

    @Override
    public String uploadComuniFromCSV(MultipartFile comuni) {
        return null;
    }

}
