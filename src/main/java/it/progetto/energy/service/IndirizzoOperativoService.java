package it.progetto.energy.service;

import it.progetto.energy.dto.indirizzo.IndirizzoDTO;
import it.progetto.energy.dto.indirizzo.IndirizzoUpdateDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.persistence.entity.Comune;
import it.progetto.energy.persistence.entity.IndirizzoOperativo;
import it.progetto.energy.persistence.repository.IndirizzoOperativoRepository;
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
public class IndirizzoOperativoService {

	@Autowired
	IndirizzoOperativoRepository indiOpRepo;
	@Autowired
	ComuneService comuneServ;
	
	/**
	 * Recupera tutti gli Indirizzi Operativi
	 * @deprecated
	 * @return
	 */
	public List<IndirizzoOperativo> getAllIndirizziOperativi() {
		return (List<IndirizzoOperativo>) indiOpRepo.findAll();
	}

	/**
	 * Recupera tutti gli Indirizzi Operativi per pagina
	 * @param page
	 * @return
	 */
	public Page<IndirizzoOperativo> getAllIndirizziOperativi(Pageable page) {
		return (Page<IndirizzoOperativo>) indiOpRepo.findAll(page);
	}

	/**
	 * Recupera un Indirizzo Operativo tramite ID
	 * @param id
	 * @return
	 * @throw NotFoundException
	 */
	public IndirizzoOperativo associaIndirizzoOperativo(Long id) {
		if (indiOpRepo.existsById(id)) {
			IndirizzoOperativo indirizzoTrovato = indiOpRepo.findById(id).get();
			return indirizzoTrovato;
		} else {
			throw new NotFoundException(ERROR_TWO);
		}
	}

	/**
	 * Inserisce un Indirizzo Operativo
	 * @param dto
	 * @throws ElementAlreadyPresentException
	 */
	public void inserisciIndirizzoOperativo(IndirizzoDTO dto) {
		if (!indiOpRepo.existsByViaAllIgnoreCase(dto.getVia()) || !indiOpRepo.existsByCivico(dto.getCivico())
				|| !indiOpRepo.existsByCap(dto.getCap())) {
			IndirizzoOperativo indirizzo = new IndirizzoOperativo();
			BeanUtils.copyProperties(dto, indirizzo);
			String localita = dto.getLocalita();
			localita.toUpperCase();
			Comune comuneTrovato = comuneServ.associaComune(localita);
			indirizzo.setComune(comuneTrovato);
			comuneTrovato.getIndirizziOperativi().add(indirizzo);
			indiOpRepo.save(indirizzo);
			log.info("L'indirizzo Operativo è stato salvato");
		} else {
			throw new ElementAlreadyPresentException(ERROR_ONE);
		}
	}

	/**
	 * Modifica un Indirizzo Operativo
	 * @param dto
	 * @Throw NotFoundException
	 */
	public void modificaIndirizzoOperativo(IndirizzoUpdateDTO dto) {
		if (indiOpRepo.existsById(dto.getIdIndirizzo())) {
			IndirizzoOperativo indirizzo = indiOpRepo.findById(dto.getIdIndirizzo()).get();
			BeanUtils.copyProperties(dto, indirizzo);
			String localita = dto.getLocalita();
			localita.toUpperCase();
			Comune comuneTrovato = comuneServ.associaComune(localita);
			indirizzo.setComune(comuneTrovato);
			comuneTrovato.getIndirizziOperativi().add(indirizzo);
			indiOpRepo.save(indirizzo);
			log.info("l'indirizzo Operativo è stato modificato");
		} else {
			throw new NotFoundException(ERROR_TWO); //TODO
		}
	}
	/**
	 * Elimina un Indirizzo Operativo
	 * @param id
	 */
	public void eliminaIndirizzoOperativo(Long id) {
		if (indiOpRepo.existsById(id)) {
			indiOpRepo.deleteById(id);
			log.info("L'indirizzo Operativo n°" + id + " è stato eliminato");
		} else {
			throw new NotFoundException(ERROR_TWO); //TODO
		}
	}

}
