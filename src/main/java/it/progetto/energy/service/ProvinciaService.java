package it.progetto.energy.service;

import it.progetto.energy.dto.provincia.ProvinciaDTO;
import it.progetto.energy.dto.provincia.ProvinciaUpdateDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.persistence.entity.Provincia;
import it.progetto.energy.persistence.repository.ProvinciaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;
import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_TWO;

@Service
@Slf4j
public class ProvinciaService {

	@Autowired
	ProvinciaRepository provinciaRepo;

	/**
	 * Recupera tutte le Province
	 * @deprecated
	 * @return
	 */
	public List<Provincia> getAllProvince() {
		return (List<Provincia>) provinciaRepo.findAll();
	}

	/**
	 * Recupera tutte le Province per pagina
	 * @param page
	 * @return
	 */
	public Page<Provincia> getAllProvince(Pageable page) {
		return provinciaRepo.findAll(page);
	}

	/**
	 * Recupera una provincia tramite la sigla
	 * @param sigla
	 * @return Provincia
	 */
	public Provincia associaProvincia(String sigla) {
		if (provinciaRepo.existsBySiglaAllIgnoreCase(sigla)) {
			Provincia provinciaTrovata = provinciaRepo.findBySiglaAllIgnoreCase(sigla);
			return provinciaTrovata;
		} else {
			throw new NotFoundException(ERROR_ONE);
		}
	}

	/**
	 * Inserisce una Provincia
	 * @param provinciaDTO
	 * @throws ElementAlreadyPresentException
	 */
	public void inserisciProvincia(ProvinciaDTO provinciaDTO) throws ElementAlreadyPresentException {
		if (!provinciaRepo.existsBySiglaAllIgnoreCase(provinciaDTO.getSigla())) {
			Provincia provincia = new Provincia();
			BeanUtils.copyProperties(provinciaDTO, provincia);
			provinciaRepo.save(provincia);
			log.info("La provincia è stata salvata");
		} else {
			throw new ElementAlreadyPresentException(ERROR_TWO);
		}
	}

	/**
	 * Modifica una Provincia
	 * @param provinciaUpdateDTO
	 */
	public void modificaProvincia(ProvinciaUpdateDTO provinciaUpdateDTO) {
		if (provinciaRepo.existsBySiglaAllIgnoreCase(provinciaUpdateDTO.getSigla())) {
			Provincia provincia = provinciaRepo.findBySiglaAllIgnoreCase(provinciaUpdateDTO.getSigla());
			BeanUtils.copyProperties(provinciaUpdateDTO, provincia);
			provinciaRepo.save(provincia);
			log.info("La Provincia è stata modificata");
		} else {
			throw new NotFoundException(ERROR_ONE); //TODO
		}
	}

	/**
	 * Elimina una Provincia
	 * @param id
	 */
	public void eliminaProvincia(Long id) {
		if (provinciaRepo.existsById(id)) {
			provinciaRepo.deleteById(id);
			log.info("La Provincia id " + id + " è stata eliminata");
		} else {
			throw new NotFoundException(ERROR_ONE); //TODO
		}
	}

}
