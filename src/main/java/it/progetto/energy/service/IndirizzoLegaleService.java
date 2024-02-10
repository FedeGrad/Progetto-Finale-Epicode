package it.progetto.energy.service;

import it.progetto.energy.dto.indirizzo.IndirizzoDTO;
import it.progetto.energy.dto.indirizzo.IndirizzoUpdateDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.persistence.entity.Comune;
import it.progetto.energy.persistence.entity.IndirizzoLegale;
import it.progetto.energy.persistence.repository.IndirizzoLegaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;
import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_TWO;

@Service
@Slf4j
@RequiredArgsConstructor
public class IndirizzoLegaleService {

	private final IndirizzoLegaleRepository indirizzoLegaleRepository;
	private final ComuneService comuneServ;

	/**
	 * Recupera tutti gli Indirizzi Legali
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public List<IndirizzoLegale> getAllIndirizziLegali() {
		return (List<IndirizzoLegale>) indirizzoLegaleRepository.findAll();
	}

	/**
	 * Recupera tutti gli Indirizzi Legali per pagina
	 * @param page
	 * @return
	 */
	public Page<IndirizzoLegale> getAllIndirizziLegali(Pageable page) {
		return (Page<IndirizzoLegale>) indirizzoLegaleRepository.findAll(page);
	}

	/**
	 * Recupera un indirizzo Legale per ID
	 * @param id
	 * @return IndirizzoLegale
	 */
	public IndirizzoLegale associaIndirizzoLegale(Long id) {
		return indirizzoLegaleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ERROR_ONE));
	}

	/**
	 * Inserisce un Indirizzo Legale
	 * @param dto
	 * @throws ElementAlreadyPresentException
	 */
	public void inserisciIndirizzoLegale(IndirizzoDTO dto) throws ElementAlreadyPresentException {
		if (!indirizzoLegaleRepository.existsByViaAllIgnoreCase(dto.getVia())
				|| !indirizzoLegaleRepository.existsByCivico(dto.getCivico())
				|| !indirizzoLegaleRepository.existsByCap(dto.getCap())) {
			IndirizzoLegale indirizzo = new IndirizzoLegale();
			BeanUtils.copyProperties(dto, indirizzo);
			String localita = dto.getLocalita();
			Comune comuneTrovato = comuneServ.associaComune(localita.toUpperCase());
			indirizzo.setComune(comuneTrovato);
			comuneTrovato.getIndirizziLegali().add(indirizzo);

			indirizzoLegaleRepository.save(indirizzo);
			log.info("L'indirizzo Legale è stato salvato");
		} else {
			throw new ElementAlreadyPresentException(ERROR_TWO);
		}
	}

	/**
	 * Modifica un Indirizzo Legale
	 * @param dto
	 * @throws NotFoundException
	 */
	public void updateMainAddress(IndirizzoUpdateDTO dto) {
		if (indirizzoLegaleRepository.existsById(dto.getIdIndirizzo())) {
			IndirizzoLegale indirizzo = indirizzoLegaleRepository.findById(dto.getIdIndirizzo())
					.orElseThrow(() -> new NotFoundException(ERROR_ONE)); //TODO
			BeanUtils.copyProperties(dto, indirizzo);
			String localita = dto.getLocalita();
			Comune comuneTrovato = comuneServ.associaComune(localita.toUpperCase());
			indirizzo.setComune(comuneTrovato);
			comuneTrovato.getIndirizziLegali().add(indirizzo);
			indirizzoLegaleRepository.save(indirizzo);
			log.info("l'indirizzo Legale è stato modificato");
		} else {
			throw new NotFoundException(ERROR_ONE); //TODO
		}
	}

	/**
	 * Elimina un Indirizzo Legale
	 * @param id
	 * @throws NotFoundException
	 */
	public void deleteMainAddress(Long id) {
		if (indirizzoLegaleRepository.existsById(id)) {
			indirizzoLegaleRepository.deleteById(id);
			log.info("L'indirizzo Legale id: {} è stato eliminato", id);
		} else {
			throw new NotFoundException(ERROR_ONE); //TODO
		}
	}

}
