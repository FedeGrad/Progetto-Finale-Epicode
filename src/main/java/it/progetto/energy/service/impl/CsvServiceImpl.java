package it.progetto.energy.service.impl;

import it.progetto.energy.business.CsvExtractor;
import it.progetto.energy.csv.ComuneCSV;
import it.progetto.energy.csv.ProvinciaCSV;
import it.progetto.energy.mapper.csvtoentiy.ComuneCSVMapper;
import it.progetto.energy.mapper.csvtoentiy.ProvinciaCSVMapper;
import it.progetto.energy.model.CsvImportedDomain;
import it.progetto.energy.persistence.entity.ComuneEntity;
import it.progetto.energy.persistence.entity.ProvinciaEntity;
import it.progetto.energy.persistence.repository.ComuneRepository;
import it.progetto.energy.persistence.repository.ProvinciaRepository;
import it.progetto.energy.service.CsvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvServiceImpl implements CsvService {

	private final CsvExtractor csvExtractor;
	private final ProvinciaRepository provinciaRepository;
	private final ComuneRepository comuneRepository;
	private final ProvinciaCSVMapper provinciaCSVMapper;
	private final ComuneCSVMapper comuneCSVMapper;

	@Override
	public CsvImportedDomain uploadProvinceAndComuniFromCSV(MultipartFile province, MultipartFile comuni) {
		List<ProvinciaEntity> provinceSaved = uploadProvince(province);
		List<ComuneEntity> comuniSaved = uploadComuniAndJoinWithProvince(comuni);

		return new CsvImportedDomain((long) provinceSaved.size(), (long) comuniSaved.size());
	}

	@Override
	public CsvImportedDomain uploadProvinceFromCSV(MultipartFile province) {
		List<ProvinciaEntity> provinceSaved = uploadProvince(province);
		return new CsvImportedDomain((long) provinceSaved.size(), null);
	}

	@Override
	public CsvImportedDomain uploadComuniFromCSV(MultipartFile comuni) {
		List<ComuneEntity> comuniSaved = uploadComuniAndJoinWithProvince(comuni);
		return new CsvImportedDomain(null, (long) comuniSaved.size());

	}

	private List<ComuneEntity> uploadComuniAndJoinWithProvince(MultipartFile comuni) {
		List<ComuneCSV> comuneCSVList = csvExtractor
				.extractDataFromMultipartFileToListCsv(comuni, ComuneCSV.class);
		List<ComuneEntity> comuneEntityList = comuneCSVMapper.fromComuneCSVListToComuneEntityList(comuneCSVList);
		List<ComuneEntity> comuneEntitiesToSave = new ArrayList<>();
		for (ComuneEntity comuneEntity : comuneEntityList) {
			ProvinciaEntity provincia = provinciaRepository.findBySiglaAllIgnoreCase(comuneEntity.getProvincia().getSigla())
					.stream()
					.findFirst()
					.orElse(null);
			comuneEntity.setProvincia(provincia);
			if (!comuneRepository.existsByName(comuneEntity.getName())) {
				comuneEntitiesToSave.add(comuneEntity);
			}
		}
		List<ComuneEntity> saved = comuneRepository.saveAll(comuneEntitiesToSave);
		log.info("{} Comuni added", saved.size());
		return saved;
	}

	private List<ProvinciaEntity> uploadProvince(MultipartFile province) {
		List<ProvinciaCSV> provinciaCSVList = csvExtractor
				.extractDataFromMultipartFileToListCsv(province, ProvinciaCSV.class);

		List<ProvinciaEntity> provinciaEntityList = provinciaCSVMapper
				.fromProvinciaCSVListToProvinciaEntityList(provinciaCSVList).stream()
				.filter(provinciaEntity -> provinciaRepository.existsBySiglaAllIgnoreCase(provinciaEntity.getSigla()))
				.toList();

		List<ProvinciaEntity> saved = provinciaRepository.saveAll(provinciaEntityList);
		log.info("{} Provincie added", saved.size());
		return provinciaEntityList;
	}

}
