package it.progetto.energy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.FatturaDTO;
import it.progetto.energy.dto.FatturaModificaDTO;
import it.progetto.energy.dto.RangeDTO;
import it.progetto.energy.model.Cliente;
import it.progetto.energy.model.Fattura;
import it.progetto.energy.model.StatoFattura;
import it.progetto.energy.repository.FatturaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
//@Data
//@AllArgsConstructor
@Slf4j
@Tag(name = "Controller Fattura", description = "Gestione delle fatture")
public class FatturaService {

	@Autowired
	FatturaRepository fatturaRepo;
	@Autowired
	ClienteService clienteServ;

	/**
	 * Recupera tutte le fatture
	 * 
	 * @deprecated
	 * @return
	 */
	public List<Fattura> getAllFatture() {
		return (List<Fattura>) fatturaRepo.findAll();
	}

	/**
	 * Recupera tutte le fatture, paginate
	 * 
	 * @param page
	 * @return
	 */
	public Page<Fattura> getAllFatture(Pageable page) {
		return (Page<Fattura>) fatturaRepo.findAll(page);
	}

	/**
	 * Recupera tutte le fatture di un CLiente
	 * 
	 * @param idCliente
	 * @param page
	 * @return
	 */
	public List<Fattura> getFatturaByCliente(Long idCliente) {
		return (List<Fattura>) fatturaRepo.findByCliente(idCliente);
	}

	/**
	 * Recupera tutte le fatture di un determinato importo
	 * 
	 * @param dto
	 * @param page
	 * @return
	 */
	public Page<Fattura> getFatturaByImporto(RangeDTO dto, Pageable page) {
		return (Page<Fattura>) fatturaRepo.findByImportoBetween(dto.getImportoMin(), dto.getImportoMax(), page);
	}

	/**
	 * Recupera tutte le fatture in un determinato stato
	 * 
	 * @param stato
	 * @param page
	 * @return
	 */
	public Page<Fattura> getFatturaByStato(StatoFattura stato, Pageable page) {
		return (Page<Fattura>) fatturaRepo.findByStatoAllIgnoreCase(stato, page);
	}

	/**
	 * Recupera tutte le fatture per data
	 * 
	 * @param data
	 * @param page
	 * @return
	 */
	public Page<Fattura> getFatturaByData(DataDTO data, Pageable page) {
		return (Page<Fattura>) fatturaRepo.findByData(data.getData(), page);
	}

	/**
	 * Recupera tutte le fatture per anno
	 * 
	 * @param anno
	 * @param page
	 * @return
	 */
	public Page<Fattura> getFatturaByAnno(Integer anno, Pageable page) {
		return (Page<Fattura>) fatturaRepo.findByAnno(anno, page);
	}

	/**
	 * Inserisce una Fattura nel sistema
	 * 
	 * @param dto
	 */
	public void inserisciFattura(FatturaDTO dto) {
		Fattura fattura = new Fattura();
		String stato = dto.getStato();
		switch (stato.toUpperCase()) {
			case "PAGATA":
				fattura.setStato(StatoFattura.PAGATA);
				break;
			case "NON PAGATA":
				fattura.setStato(StatoFattura.NON_PAGATA);
				break;
			case "ANNULLATA":
				fattura.setStato(StatoFattura.ANNULLATA);
				break;
			case "SCADUTA":
				fattura.setStato(StatoFattura.SCADUTA);
				break;
			case "DA RIMBORSARE":
				fattura.setStato(StatoFattura.DA_RIMBORSARE);
				break;
			case "RIMBORSATA":
				fattura.setStato(StatoFattura.RIMBORSATA);
				break;
		}
		BeanUtils.copyProperties(dto, fattura);
		// fatturaRepo.save(fattura);
		Cliente cliente = clienteServ.associaCliente(dto.getIdCliente());
		cliente.getFatture().add(fattura);
		fattura.setCliente(cliente);
		log.info("Cliente associato");
		fatturaRepo.save(fattura);
		log.info("La Fattura è stata salvata");
	}

	/**
	 * Modifica una Fattura
	 * 
	 * @param dto
	 * @throws NotFoundException
	 */
	public void modificaFattura(FatturaModificaDTO dto) throws NotFoundException {
		if (fatturaRepo.existsById(dto.getIdFattura())) {
			Fattura fattura = fatturaRepo.findById(dto.getIdFattura()).get();
			String stato = dto.getStato();
			switch (stato.toUpperCase()) {
				case "PAGATA ":
					fattura.setStato(StatoFattura.PAGATA);
					break;
				case "NON PAGATA":
					fattura.setStato(StatoFattura.NON_PAGATA);
					break;
				case "ANNULLATA":
					fattura.setStato(StatoFattura.ANNULLATA);
					break;
				case "SCADUTA":
					fattura.setStato(StatoFattura.SCADUTA);
					break;
				case "DA RIMBORSARE":
					fattura.setStato(StatoFattura.DA_RIMBORSARE);
					break;
				case "RIMBORSATA":
					fattura.setStato(StatoFattura.RIMBORSATA);
					break;
			}
			BeanUtils.copyProperties(dto, fattura);
			// fatturaRepo.save(fattura);
			Cliente cliente = clienteServ.associaCliente(dto.getIdCliente());
			cliente.getFatture().add(fattura);
			fattura.setCliente(cliente);
			log.info("Cliente associato");
			fatturaRepo.save(fattura);
			log.info("La Fattura è stata modificata");
		} else {
			throw new NotFoundException("La Fattura id " + dto.getIdFattura() + " non esiste");
		}
	}

	/**
	 * Elimina una Fattura
	 * 
	 * @param id
	 */
	public void eliminaFattura(Long id) {
		if (fatturaRepo.existsById(id)) {
			fatturaRepo.deleteById(id);
			log.info("La Fattura id " + id + " è stata eliminata");
		} else {
			throw new NotFoundException("La Fattura id" + id + " non esiste");
		}
	}

}
