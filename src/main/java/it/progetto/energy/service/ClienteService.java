package it.progetto.energy.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import it.progetto.energy.model.ClientDomain;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import it.progetto.energy.dto.ClienteDTO;
import it.progetto.energy.dto.ClienteModificaDTO;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.RicercaProvinciaDTO;
import it.progetto.energy.exception.WrongInsertException;
import it.progetto.energy.model.IndirizzoLegale;
import it.progetto.energy.model.IndirizzoOperativo;
import it.progetto.energy.model.Tipologia;
import it.progetto.energy.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;

@Service
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
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public List<ClientDomain> getAllClienti() {
		return (List<ClientDomain>) clienteRepo.findAll();
	}

	/**
	 * Recupera tutti i Clienti, per pagina
	 * @param page
	 * @return
	 */
	public Page<ClientDomain> getAllClienti(Pageable page) {
		return (Page<ClientDomain>) clienteRepo.findAll(page);
	}

	/**
	 * Recupera i Clienti per nome
	 * @param nome
	 * @param page
	 * @return
	 */
	public Page<ClientDomain> getClientiByNome(String nome, Pageable page) {
		return (Page<ClientDomain>) clienteRepo.findByNomeContattoAllIgnoreCase(nome, page);
	}

	/**
	 * Recupera i Clienti che nel nome è presente il valore passato nel parametro, nel nome
	 * @param nomeContiene
	 * @return
	 */
	public Page<ClientDomain> getClientiByNomeContain(String nomeContiene, Pageable page) {
		return (Page<ClientDomain>) clienteRepo.findByNomeContattoContainingAllIgnoreCase(nomeContiene, page);
	}

	/**
	 * Recupera i Clienti con uno specifico fatturato
	 * @param fatturato
	 * @param page
	 * @return
	 */
	public Page<ClientDomain> getClientiByFatturato(Double fatturato, Pageable page) {
		return (Page<ClientDomain>) clienteRepo.findByFatturatoAnnuale(fatturato, page);
	}

	/**
	 * Recupera i Clienti registrati in una determinata data
	 * @param dataInserimento
	 * @param page
	 * @return
	 */
	public Page<ClientDomain> getClientiByDataInserimento(DataDTO dataInserimento, Pageable page) {
		return (Page<ClientDomain>) clienteRepo.findByDataInserimento(dataInserimento.getData(), page);
	}

	/**
	 * Recupera i Clienti contattati in una determinata data
	 * @param dataUltContatto
	 * @param page
	 * @return
	 */
	public Page<ClientDomain> getClientiByDataUltimoContatto(DataDTO dataUltContatto, Pageable page) {
		return (Page<ClientDomain>) clienteRepo.findByDataUltimoContatto(dataUltContatto.getData(), page);
	}

	/**
	 * Recupera i Clienti di una specifica provincia
	 * @param ricercaProvinciaDTO
	 * @return
	 */
	public List<ClientDomain> getClientiByProvincia(RicercaProvinciaDTO ricercaProvinciaDTO) {
		return (List<ClientDomain>) clienteRepo.findByProvinciaAllIgnoreCase(ricercaProvinciaDTO.getProvincia());
	}

	/**
	 * Associa un cliente by id
	 * @param id
	 * @return
	 */
	public ClientDomain associaCliente(Long id) {
		if (clienteRepo.existsById(id)) {
			ClientDomain clientDomainTrovato = clienteRepo.findById(id).get();
			return clientDomainTrovato;
		} else {
			throw new NotFoundException("Cliente id " + id + " non trovato");
		}
	}

	/**
	 * RegEx per: [0]eMail, [1]numero, [2]partita Iva di un Utente
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
			log.info("Errore inserimento partita IVA");
			return false;
		}
		while (!valori[4].matches("[0-9]{10,12}") || !valori[5].matches("[0-9]{10,12}")) {
			log.info("Errore inserimento telefono");
			return false;
		}
		return true;
	}

	/**
	 * Inserisce un Cliente nel sistema
	 * @param clienteDTO
	 * @throws WrongInsertException
	 */
	public ResponseEntity<?> inserisciCliente(ClienteDTO clienteDTO) throws WrongInsertException {
		ClientDomain clientDomain;
		if (controlloDatiCliente(clienteDTO.getEmail(), clienteDTO.getEmailContatto(), clienteDTO.getPec(),
				clienteDTO.getPartitaIva(), clienteDTO.getTelefono(), clienteDTO.getTelefonoContatto())) {

			clientDomain = ClientDomain.builder()
//					.dataDiNascita(clienteDTO.getDataDiNascita())
					.dataInserimento(LocalDate.now())
					.dataUltimoContatto(LocalDate.now())
					.anni(Period.between(clienteDTO.getDataDiNascita(), LocalDate.now()).getYears())
					.build();

			switch (clienteDTO.getTipologia().toUpperCase()) {
				case "PA": clientDomain.setTipologia(Tipologia.PA); break;
				case "SAS": clientDomain.setTipologia(Tipologia.SAS); break;
				case "SPA": clientDomain.setTipologia(Tipologia.SPA); break;
				case "SRL": clientDomain.setTipologia(Tipologia.SRL); break;
			}
			BeanUtils.copyProperties(clienteDTO, clientDomain);
			IndirizzoLegale indirizzoLegTrovato = indirizzoLegServ.associaIndirizzoLegale(clienteDTO.getIdIndirizzoLegale());
			clientDomain.setIndirizzoLegale(indirizzoLegTrovato);
			indirizzoLegTrovato.setClientDomain(clientDomain);
			log.info("Indirizzo Legale associato");
			IndirizzoOperativo indirizzoOpTrovato = indirizzoOpServ
					.associaIndirizzoOperativo(clienteDTO.getIdIndirizzoOperativo());
			clientDomain.setIndirizzoOperativo(indirizzoOpTrovato);
			indirizzoOpTrovato.setClientDomain(clientDomain);
			log.info("Indirizzo Operativo associato");
			clienteRepo.save(clientDomain);
			log.info("Cliente: {}, salvato  in data: {}",
					clientDomain.getNomeContatto() + " " + clientDomain.getCognomeContatto(), clientDomain.getDataInserimento());
			return ResponseEntity.ok("Cliente creato id=" + clientDomain.getId());
		} else {
			throw new WrongInsertException("Errore inserimento dati");
		}
	}

	/**
	 * Modifica un Cliente nel sistema
	 * @param dto
	 * @throws NotFoundException
	 * @throws WrongInsertException
	 */
	public void modificaCliente(ClienteModificaDTO dto) throws NotFoundException, WrongInsertException {
		if (clienteRepo.existsById(dto.getIdCliente())) {
			ClientDomain clientDomain = clienteRepo.findById(dto.getIdCliente()).get();
			clientDomain.setDataUltimoContatto(LocalDate.now());
			switch (dto.getTipologia()) {
				case "PA": clientDomain.setTipologia(Tipologia.PA); break;
				case "SAS": clientDomain.setTipologia(Tipologia.SAS); break;
				case "SPA": clientDomain.setTipologia(Tipologia.SPA); break;
				case "SRL": clientDomain.setTipologia(Tipologia.SRL); break;
			}
			if (controlloDatiCliente(dto.getEmail(), dto.getEmailContatto(), dto.getPec(), dto.getPartitaIva(),
					dto.getTelefono(), dto.getTelefonoContatto())) {
				BeanUtils.copyProperties(dto, clientDomain);
			} else {
				throw new WrongInsertException("Errore inserimento dati");
			}
			log.info("Il Cliente in data: " + clientDomain.getDataUltimoContatto() + " è stato modificato");
			IndirizzoLegale indirizzoLegtrovato = indirizzoLegServ.associaIndirizzoLegale(dto.getIDindirizzoLegale());
			clientDomain.setIndirizzoLegale(indirizzoLegtrovato);
			indirizzoLegtrovato.setClientDomain(clientDomain);
			log.info("Indirizzo Legale associato");
			IndirizzoOperativo indirizzoOptrovato = indirizzoOpServ
					.associaIndirizzoOperativo(dto.getIDindirizzoOperativo());
			clientDomain.setIndirizzoOperativo(indirizzoOptrovato);
			indirizzoOptrovato.setClientDomain(clientDomain);
			log.info("Indirizzo Operativo associato");
			clienteRepo.save(clientDomain);
			log.info(clientDomain.getNomeContatto() + " " + clientDomain.getCognomeContatto() + " modificato");
		} else {
			throw new NotFoundException("Il Noleggio id " + dto.getIdCliente() + " non esiste");
		}
	}

	/**
	 * Elimina un cliente
	 * @param id
	 */
	public void eliminaCliente(Long id) {
		if (clienteRepo.existsById(id)) {
			ClientDomain clientDomain = clienteRepo.findById(id).get();
			log.info("Il Cliente " + clientDomain.getNomeContatto() + " " + clientDomain.getCognomeContatto() + " in data "
					+ LocalDate.now() + " è stato eliminato");
			clienteRepo.deleteById(id);
		} else {
			throw new NotFoundException("Il Cliente id" + id + " non esiste");
		}
	}

}
