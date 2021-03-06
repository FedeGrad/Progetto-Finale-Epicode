package it.progetto.energy.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.dto.ComuneDTO;
import it.progetto.energy.dto.ComuneModificaDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.model.Comune;
import it.progetto.energy.model.Provincia;
import it.progetto.energy.repository.ComuneRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
//@Data
//@AllArgsConstructor
@Slf4j
@Tag(name = "Controller Comune", description = "Gestione dei comuni")
public class ComuneService {
	@Autowired
	ComuneRepository comuneRepo;
	@Autowired
	ProvinciaService provinciaServ;

	/**
	 * Recupera tutti i Comuni, paginati
	 * 
	 * @param page
	 * @return
	 */
	public Page<Comune> getAllComuni(Pageable page) {
		return (Page<Comune>) comuneRepo.findAll(page);
	}

	/**
	 * Recupera tutti i Comuni
	 * 
	 * @deprecated
	 * @return
	 */
	public List<Comune> getAllComuni() {
		List<Comune> lista = (List<Comune>) comuneRepo.findAll();
		if (lista.size() < 0) {
			System.out.println("errore");
		}
		return lista;
	}

	/**
	 * Associa un Comune by nome
	 * 
	 * @param nome
	 * @return
	 */
	public Comune associaComune(String nome) {
		if (comuneRepo.existsByNomeAllIgnoreCase(nome)) {
			Comune comuneTrovato = comuneRepo.findByNomeAllIgnoreCase(nome);
			return comuneTrovato;
		} else {
			throw new NotFoundException("Comune " + nome + " non trovato");
		}
	}

	/**
	 * Inserisce un Comune nel sistema
	 * 
	 * @param dto
	 * @throws ElementAlreadyPresentException
	 */
	public void inserisciComune(ComuneDTO dto) throws ElementAlreadyPresentException {
		if (!comuneRepo.existsByNomeAllIgnoreCase(dto.getNome())) {
			Comune comune = new Comune();
			BeanUtils.copyProperties(dto, comune);
			Provincia provinciaTrovata = provinciaServ.associaProvincia(dto.getSiglaProvincia());
			comuneRepo.save(comune);
			log.info("Il Comune " + comune.getNome() + " ?? stato salvato");
		} else {
			throw new ElementAlreadyPresentException("Comune " + dto.getNome() + " gi?? presente");
		}
	}

	/**
	 * Modifica un Comune nel sistema
	 * 
	 * @param dto
	 * @throws ElementAlreadyPresentException
	 */
	public void modificaComune(ComuneModificaDTO dto) throws ElementAlreadyPresentException {
		if (comuneRepo.existsById(dto.getIdComune())) {
			Comune comune = comuneRepo.findById(dto.getIdComune()).get();
			BeanUtils.copyProperties(dto, comune);
			Provincia provinciaTrovata = provinciaServ.associaProvincia(dto.getSiglaProvincia());
			comuneRepo.save(comune);
			log.info("Il Comune con l'id" + dto.getIdComune() + " ?? stato modificato");
		} else {
			throw new NotFoundException("Il Comune con l'id " + dto.getIdComune() + " non ?? presente nel sistema");
		}
	}

	/**
	 * Elimina un Comune dal sistema
	 * 
	 * @param id
	 */
	public void eliminaComune(Long id) {
		if (comuneRepo.existsById(id)) {
			Comune comune = comuneRepo.findById(id).get();
			log.info("Il Comune" + comune.getNome() + " ?? stato eliminato");
			comuneRepo.deleteById(id);
		} else {
			throw new NotFoundException("Il Comune con l'id " + id + " non presente nel sistema");
		}
	}

}
