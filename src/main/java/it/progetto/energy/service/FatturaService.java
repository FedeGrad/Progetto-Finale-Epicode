package it.progetto.energy.service;

import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.RangeDTO;
import it.progetto.energy.dto.fattura.FatturaDTO;
import it.progetto.energy.dto.fattura.FatturaModificaDTO;
import it.progetto.energy.dto.fattura.FatturaPDFDTO;
import it.progetto.energy.persistence.entity.Cliente;
import it.progetto.energy.persistence.entity.Fattura;
import it.progetto.energy.persistence.entity.StatoFattura;
import it.progetto.energy.persistence.repository.FatturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class FatturaService extends FileService{

	@Autowired
	FatturaRepository fatturaRepo;
	@Autowired
	ClienteService clienteServ;

	/**
	 * Recupera tutte le fatture
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
	 * @param idCliente
	 * @return
	 */
	public List<Fattura> getFatturaByCliente(Long idCliente) {
		return fatturaRepo.findByCliente(idCliente);
	}

	/**
	 * Recupera tutte le fatture di un determinato importo
	 * @param rangeDTO
	 * @param page
	 * @return
	 */
	public Page<Fattura> getFatturaByImporto(RangeDTO rangeDTO, Pageable page) {
		return fatturaRepo.findByImportoBetween(rangeDTO.getImportoMin(), rangeDTO.getImportoMax(), page);
	}

	/**
	 * Recupera tutte le fatture in un determinato stato
	 * @param stato
	 * @param page
	 * @return
	 */
	public Page<Fattura> getFatturaByStato(StatoFattura stato, Pageable page) {
		return fatturaRepo.findByStatoAllIgnoreCase(stato, page);
	}

	/**
	 * Recupera tutte le fatture per data
	 * @param data
	 * @param page
	 * @return
	 */
	public Page<Fattura> getFatturaByData(DataDTO data, Pageable page) {
		return (Page<Fattura>) fatturaRepo.findByData(data.getData(), page);
	}

	/**
	 * Recupera tutte le fatture per anno
	 * @param anno
	 * @param page
	 * @return
	 */
	public Page<Fattura> getFatturaByAnno(Integer anno, Pageable page) {
		return (Page<Fattura>) fatturaRepo.findByAnno(anno, page);
	}

	/**
	 * Inserisce una Fattura nel sistema
	 * @param dto
	 */
	public void inserisciFattura(FatturaDTO dto) throws IOException {
		Fattura fattura = new Fattura();
		String stato = dto.getStato();
		switch (stato.toUpperCase().replaceAll(" ", "").trim()) {
			case "PAGATA": fattura.setStato(StatoFattura.PAGATA); break;
			case "NONPAGATA": fattura.setStato(StatoFattura.NON_PAGATA);break;
			case "ANNULLATA": fattura.setStato(StatoFattura.ANNULLATA); break;
			case "SCADUTA": fattura.setStato(StatoFattura.SCADUTA); break;
			case "DARIMBORSARE": fattura.setStato(StatoFattura.DA_RIMBORSARE); break;
			case "RIMBORSATA": fattura.setStato(StatoFattura.RIMBORSATA); break;
		}
		BeanUtils.copyProperties(dto, fattura);
		Path root = Paths.get("upload");
		Cliente cliente = clienteServ.associaCliente(dto.getIdCliente());
		cliente.getFatture().add(fattura);
		fattura.setCliente(cliente);
		log.info("Cliente associato");
		fatturaRepo.save(fattura);
		log.info("La Fattura è stata salvata");
	}

	//TODO IMPLEMENTARE
	public ResponseEntity<?> inserisciFatturaPDF(FatturaPDFDTO fatturaPDFDTO) throws IOException {
		Path root = Paths.get("upload");
		File file = new File(root.toUri());
		fatturaPDFDTO.getFileFattura().transferTo(file);
		Fattura fattura = fatturaRepo.findById(fatturaPDFDTO.getIdFattura()).get();
		fattura.setFile(file);
		save(fatturaPDFDTO.getFileFattura());
		log.info("CV SALVATO");

		return ResponseEntity.ok().build();
	}

	/**
	 * Modifica una Fattura
	 * @param dto
	 * @throws NotFoundException
	 */
	public void modificaFattura(FatturaModificaDTO dto) throws NotFoundException {
		if (fatturaRepo.existsById(dto.getIdFattura())) {
			Fattura fattura = fatturaRepo.findById(dto.getIdFattura()).get();
			String stato = dto.getStato();
			switch (stato.toUpperCase().replaceAll(" ", "").trim()) {
				case "PAGATA": fattura.setStato(StatoFattura.PAGATA); break;
				case "NONPAGATA": fattura.setStato(StatoFattura.NON_PAGATA);break;
				case "ANNULLATA": fattura.setStato(StatoFattura.ANNULLATA); break;
				case "SCADUTA": fattura.setStato(StatoFattura.SCADUTA); break;
				case "DARIMBORSARE": fattura.setStato(StatoFattura.DA_RIMBORSARE); break;
				case "RIMBORSATA": fattura.setStato(StatoFattura.RIMBORSATA); break;
			}
			BeanUtils.copyProperties(dto, fattura);
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
