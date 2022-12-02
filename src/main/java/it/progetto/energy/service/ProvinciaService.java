package it.progetto.energy.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.dto.ProvinciaDTO;
import it.progetto.energy.dto.ProvinciaModificaDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.model.Fattura;
import it.progetto.energy.model.Provincia;
import it.progetto.energy.repository.ProvinciaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
			throw new NotFoundException("Provincia sigla " + sigla + " non trovata");
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
			throw new ElementAlreadyPresentException("La Provincia gia presente");
		}
	}

	/**
	 * Modifica una Provincia
	 * @param provinciaModificaDTO
	 */
	public void modificaProvincia(ProvinciaModificaDTO provinciaModificaDTO) {
		if (provinciaRepo.existsBySiglaAllIgnoreCase(provinciaModificaDTO.getSigla())) {
			Provincia provincia = provinciaRepo.findBySiglaAllIgnoreCase(provinciaModificaDTO.getSigla());
			BeanUtils.copyProperties(provinciaModificaDTO, provincia);
			provinciaRepo.save(provincia);
			log.info("La Provincia è stata modificata");
		} else {
			throw new NotFoundException(
					"La Provincia sigla " + provinciaModificaDTO.getSigla() + " non è presente nel sistema");
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
			throw new NotFoundException("La Provincia id " + id + " non presente nel sistema");
		}
	}

}
