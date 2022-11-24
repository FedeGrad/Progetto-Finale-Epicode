package it.progetto.energy.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import it.progetto.energy.dto.ClienteDTO;
import it.progetto.energy.dto.ClienteModificaDTO;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.RicercaProvinciaDTO;
import it.progetto.energy.exception.WrongInsertException;
import it.progetto.energy.model.Cliente;
import it.progetto.energy.model.IndirizzoLegale;
import it.progetto.energy.model.IndirizzoOperativo;
import it.progetto.energy.model.Tipologia;
import it.progetto.energy.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;

@Service
//@Data
//@AllArgsConstructor
@Slf4j
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepo;
	@Autowired
	IndirizzoLegaleService indirizzoLegServ;
	@Autowired
	IndirizzoOperativoService indirizzoOpServ;

	/**
	 * Recupera tutti i Clienti
	 * 
	 * @deprecated
	 * @return
	 */
	public List<Cliente> getAllClienti() {
		return (List<Cliente>) clienteRepo.findAll();
	}

	/**
	 * Recupera tutti i Clienti, paginati
	 * 
	 * @param page
	 * @return
	 */
	public Page<Cliente> getClientiPaginati(Pageable page) {
		return (Page<Cliente>) clienteRepo.findAll(page);
	}

	/**
	 * Recupera i Clienti per nome
	 * 
	 * @param nome
	 * @param page
	 * @return
	 */
	public Page<Cliente> getClientiByNome(String nome, Pageable page) {
		return (Page<Cliente>) clienteRepo.findByNomeContattoAllIgnoreCase(nome, page);
	}

	/**
	 * Recupera i Clienti che contengono il valore passato come parametro, nel nome
	 * 
	 * @param nome
	 * @return
	 */
	public Page<Cliente> getClientiByNomeContain(String nome, Pageable page) {
		return (Page<Cliente>) clienteRepo.findByNomeContattoContainingAllIgnoreCase(nome, page);
	}

	/**
	 * Recupera i Clienti con uno specifico fatturato
	 * 
	 * @param fatturato
	 * @param page
	 * @return
	 */
	public Page<Cliente> getClientiByFatturato(Double fatturato, Pageable page) {
		return (Page<Cliente>) clienteRepo.findByFatturatoAnnuale(fatturato, page);
	}

	/**
	 * Recupera i Clienti registrati in una determinata data
	 * 
	 * @param dataInserimento
	 * @param page
	 * @return
	 */
	public Page<Cliente> getClientiByDataInserimento(DataDTO data, Pageable page) {
		return (Page<Cliente>) clienteRepo.findByDataInserimento(data.getData(), page);
	}

	/**
	 * Recupera i Clienti contattati in una determinata data
	 * 
	 * @param dataUltimoContatto
	 * @param page
	 * @return
	 */
	public Page<Cliente> getClientiByDataUltimoContatto(DataDTO data, Pageable page) {
		return (Page<Cliente>) clienteRepo.findByDataUltimoContatto(data.getData(), page);
	}

	/**
	 * Recupera i Clienti di una specifica provincia
	 * 
	 * @param provincia
	 * @param page
	 * @return
	 */
	public List<Cliente> getClientiByProvincia(RicercaProvinciaDTO dto) {
		return (List<Cliente>) clienteRepo.findByProvinciaAllIgnoreCase(dto.getProvincia());
	}

	/**
	 * Associa un cliente by id
	 * 
	 * @param id
	 * @return
	 */
	public Cliente associaCliente(Long id) {
		if (clienteRepo.existsById(id)) {
			Cliente clienteTrovato = clienteRepo.findById(id).get();
			return clienteTrovato;
		} else {
			throw new NotFoundException("Cliente id " + id + " non trovato");
		}
	}

	/**
	 * Varie RegEx che verificano: eMail, numero e partita Iva di un utente
	 * 
	 * @param valori
	 * @return
	 */
	private boolean controlloDatiCliente(String... valori) {
		while (!valori[0].matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")
				|| !valori[1].matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")
				|| !valori[2].matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
			log.info("Errore inserimeto E-mail");
			return false;
		}
		while (!valori[3].matches("[0-9]{11}")) {
			log.info("Errore inserimeto partita IVA");
			return false;
		}
		while (!valori[4].matches("[0-9]{10,12}") || !valori[5].matches("[0-9]{10,12}")) {
			log.info("Errore inserimeto telefono");
			return false;
		}
		return true;
	}

	/**
	 * Inserisce un Cliente nel sistema
	 * 
	 * @param dto
	 * @throws WrongInsertException
	 */
	public long inserisciCliente(ClienteDTO dto) throws WrongInsertException {
		Cliente cliente = new Cliente();
		cliente.setDataDiNascita(dto.getDataDiNascita());
		cliente.setDataInserimento(LocalDate.now());
		cliente.setDataUltimoContatto(LocalDate.now());
		cliente.setAnni(Period.between(cliente.getDataDiNascita(), LocalDate.now()).getYears());
		switch (dto.getTipologia().toUpperCase()) {
			case "PA":
				cliente.setTipologia(Tipologia.PA);
				break;
			case "SAS":
				cliente.setTipologia(Tipologia.SAS);
				break;
			case "SPA":
				cliente.setTipologia(Tipologia.SPA);
				break;
			case "SRL":
				cliente.setTipologia(Tipologia.SRL);
				break;
		}
		if (controlloDatiCliente(dto.getEmail(), dto.getEmailContatto(), dto.getPec(), dto.getPartitaIva(),
				dto.getTelefono(), dto.getTelefonoContatto())) {
			BeanUtils.copyProperties(dto, cliente);
		} else {
			throw new WrongInsertException("Errore inserimento dati");
		}
		log.info("Il Cliente inserito in data: " + cliente.getDataInserimento() + " è stato salvato");
		IndirizzoLegale indirizzoLegtrovato = indirizzoLegServ.associaIndirizzoLegale(dto.getIDindirizzoLegale());
		cliente.setIndirizzoLegale(indirizzoLegtrovato);
		indirizzoLegtrovato.setCliente(cliente);
		log.info("Indirizzo Legale associato");
		IndirizzoOperativo indirizzoOptrovato = indirizzoOpServ
				.associaIndirizzoOperativo(dto.getIDindirizzoOperativo());
		cliente.setIndirizzoOperativo(indirizzoOptrovato);
		indirizzoOptrovato.setCliente(cliente);
		log.info("Indirizzo Operativo associato");
		clienteRepo.save(cliente);
		log.info(cliente.getNomeContatto() + " " + cliente.getCognomeContatto() + " salvato");
		return cliente.getId();
	}

	/**
	 * Modifica un Cliente nel sistema
	 * 
	 * @param dto
	 * @throws NotFoundException
	 * @throws WrongInsertException
	 */
	public void modificaCliente(ClienteModificaDTO dto) throws NotFoundException, WrongInsertException {
		if (clienteRepo.existsById(dto.getIdCliente())) {
			Cliente cliente = clienteRepo.findById(dto.getIdCliente()).get();
			cliente.setDataUltimoContatto(LocalDate.now());
			switch (dto.getTipologia()) {
				case "PA":
					cliente.setTipologia(Tipologia.PA);
					break;
				case "SAS":
					cliente.setTipologia(Tipologia.SAS);
					break;
				case "SPA":
					cliente.setTipologia(Tipologia.SPA);
					break;
				case "SRL":
					cliente.setTipologia(Tipologia.SRL);
					break;
			}
			if (controlloDatiCliente(dto.getEmail(), dto.getEmailContatto(), dto.getPec(), dto.getPartitaIva(),
					dto.getTelefono(), dto.getTelefonoContatto())) {
				BeanUtils.copyProperties(dto, cliente);
			} else {
				throw new WrongInsertException("Errore inserimento dati");
			}
			// BeanUtils.copyProperties(dto, cliente);
			// clienteRepo.save(cliente);
			log.info("Il Cliente in data: " + cliente.getDataUltimoContatto() + " è stato modificato");
//			if(dto.getIDindirizzoLegale().equals("")) {
//				
//			}
			IndirizzoLegale indirizzoLegtrovato = indirizzoLegServ.associaIndirizzoLegale(dto.getIDindirizzoLegale());
				cliente.setIndirizzoLegale(indirizzoLegtrovato);
				indirizzoLegtrovato.setCliente(cliente);
				log.info("Indirizzo Legale associato");
			IndirizzoOperativo indirizzoOptrovato = indirizzoOpServ
					.associaIndirizzoOperativo(dto.getIDindirizzoOperativo());
			cliente.setIndirizzoOperativo(indirizzoOptrovato);
			indirizzoOptrovato.setCliente(cliente);
			log.info("Indirizzo Operativo associato");
			clienteRepo.save(cliente);
			log.info(cliente.getNomeContatto() + " " + cliente.getCognomeContatto() + " modificiato");
		} else {
			throw new NotFoundException("Il Nolegio id " + dto.getIdCliente() + " non esiste");
		}
	}

	/**
	 * Elimina un cliente
	 * 
	 * @param id
	 */
	public void eliminaCliente(Long id) {
		if (clienteRepo.existsById(id)) {
			Cliente cliente = clienteRepo.findById(id).get();
			log.info("Il Cliente " + cliente.getNomeContatto() + " " + cliente.getCognomeContatto() + " in data "
					+ LocalDate.now() + " è stato eliminato");
			clienteRepo.deleteById(id);
		} else {
			throw new NotFoundException("Il Cliente id" + id + " non esiste");
		}
	}
	
}
