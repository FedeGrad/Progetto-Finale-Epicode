package it.progetto.energy.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import it.progetto.energy.dto.IndirizzoDTO;
import it.progetto.energy.dto.IndirizzoModificaDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.model.Comune;
import it.progetto.energy.model.IndirizzoLegale;
import it.progetto.energy.repository.IndirizzoLegaleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
//@Data
//@AllArgsConstructor
@Slf4j
public class IndirizzoLegaleService {

	@Autowired
	IndirizzoLegaleRepository indiLegRepo;
	@Autowired
	ComuneService comuneServ;

	/**
	 * Recupera tutti gli Indirizzi Legali
	 * 
	 * @deprecated
	 * @return
	 */
	public List<IndirizzoLegale> getAllIndirizziLegali() {
		return (List<IndirizzoLegale>) indiLegRepo.findAll();
	}

	/**
	 * Recupera tutti gli Indirizzi Legali, paginati
	 * 
	 * @param page
	 * @return
	 */
	public Page<IndirizzoLegale> getIndirizziLegPaginati(Pageable page) {
		return (Page<IndirizzoLegale>) indiLegRepo.findAll(page);
	}

	/**
	 * Associa un Indirizzo Legale
	 * 
	 * @param id
	 * @return
	 */
	public IndirizzoLegale associaIndirizzoLegale(Long id) {
		if (indiLegRepo.existsById(id)) {
			IndirizzoLegale indirizzoTrovato = indiLegRepo.findById(id).get();
			return indirizzoTrovato;
		} else {
			throw new NotFoundException("Indirizzo Legale n°" + id + " non trovato");
		}
	}

	/**
	 * Inserisce un Indirizzo Legale
	 * 
	 * @param dto
	 * @throws ElementAlreadyPresentException
	 */
	public void inserisciIndirizzoLegale(IndirizzoDTO dto) throws ElementAlreadyPresentException {
		if (!indiLegRepo.existsByViaAllIgnoreCase(dto.getVia()) || !indiLegRepo.existsByCivico(dto.getCivico())
				|| !indiLegRepo.existsByCap(dto.getCap())) {
			IndirizzoLegale indirizzo = new IndirizzoLegale();
			BeanUtils.copyProperties(dto, indirizzo);
			String localita = dto.getLocalita();
			localita.toUpperCase();
			Comune comuneTrovato = comuneServ.associaComune(localita);
			indirizzo.setComune(comuneTrovato);
			comuneTrovato.getIndirizziLegali().add(indirizzo);
			indiLegRepo.save(indirizzo);
			log.info("L'indirizzo Legale è stato salvato");
		} else {
			throw new ElementAlreadyPresentException("Indirizzo Legale gia presente");
		}
	}

	/**
	 * Modifica un Indirizzo Legale
	 * 
	 * @param dto
	 */
	public void modificaIndirizzoLegale(IndirizzoModificaDTO dto) {
		if (indiLegRepo.existsById(dto.getIdIndirizzo())) {
			IndirizzoLegale indirizzo = indiLegRepo.findById(dto.getIdIndirizzo()).get();
			BeanUtils.copyProperties(dto, indirizzo);
			Comune comuneTrovato = comuneServ.associaComune(dto.getLocalita());
			indirizzo.setComune(comuneTrovato);
			comuneTrovato.getIndirizziLegali().add(indirizzo);
			indiLegRepo.save(indirizzo);
			log.info("l'indirizzo Legale è stato modificato");
		} else {
			throw new NotFoundException("L'Indirizzo Legale n°" + dto.getIdIndirizzo() + " non è presente nel sistema");
		}
	}

	/**
	 * Elimina un Indirizzo Legale
	 * 
	 * @param id
	 */
	public void eliminaIndirizzoLegale(Long id) {
		if (indiLegRepo.existsById(id)) {
			indiLegRepo.deleteById(id);
			log.info("L'indirizzo Legale n°" + id + " è stato eliminato");
		} else {
			throw new NotFoundException("L'indirizzo Legale n°" + id + " non presente nel sistema");
		}
	}

}
