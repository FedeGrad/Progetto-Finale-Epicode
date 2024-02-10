package it.progetto.energy.service;

import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.cliente.CustomerDTO;
import it.progetto.energy.dto.cliente.CustomerUpdateDTO;
import it.progetto.energy.dto.provincia.RicercaProvinciaDTO;
import it.progetto.energy.exception.WrongInsertException;
import it.progetto.energy.persistence.entity.Cliente;
import it.progetto.energy.persistence.entity.IndirizzoLegale;
import it.progetto.energy.persistence.entity.IndirizzoOperativo;
import it.progetto.energy.persistence.entity.Tipologia;
import it.progetto.energy.persistence.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

	private final ClienteRepository clienteRepo;
	private final IndirizzoLegaleService indirizzoLegServ;
	private final IndirizzoOperativoService indirizzoOpServ;

	/**
	 * Recupera tutti i Clienti
	 * @deprecated
     */
	@Deprecated
	public List<Cliente> getAllClienti() {
		return (List<Cliente>) clienteRepo.findAll();
	}

	/**
	 * Recupera tutti i Clienti, per pagina
	 * @param page
     */
	public Page<Cliente> getAllClienti(Pageable page) {
		return clienteRepo.findAll(page);
	}

	/**
	 * Recupera i Clienti per nome
	 * @param nome
	 * @param page
     */
	public Page<Cliente> getClientiByNome(String nome, Pageable page) {
		return clienteRepo.findByNomeContattoAllIgnoreCase(nome, page);
	}

	/**
	 * Recupera i Clienti che nel nome è presente il valore passato nel parametro, nel nome
	 * @param nomeContiene
     */
	public Page<Cliente> getClientiByNomeContain(String nomeContiene, Pageable page) {
		return clienteRepo.findByNomeContattoContainingAllIgnoreCase(nomeContiene, page);
	}

	/**
	 * Recupera i Clienti con uno specifico fatturato
	 * @param fatturato
	 * @param page
     */
	public Page<Cliente> getClientiByFatturato(Double fatturato, Pageable page) {
		return clienteRepo.findByFatturatoAnnuale(fatturato, page);
	}

	/**
	 * Recupera i Clienti registrati in una determinata data
	 * @param dataInserimento
	 * @param page
     */
	public Page<Cliente> getClientiByDataInserimento(DataDTO dataInserimento, Pageable page) {
		return clienteRepo.findByDataInserimento(dataInserimento.getData(), page);
	}

	/**
	 * Recupera i Clienti contattati in una determinata data
	 * @param dataUltContatto
	 * @param page
     */
	public Page<Cliente> getClientiByDataUltimoContatto(DataDTO dataUltContatto, Pageable page) {
		return clienteRepo.findByDataUltimoContatto(dataUltContatto.getData(), page);
	}

	/**
	 * Recupera i Clienti di una specifica provincia
	 * @param ricercaProvinciaDTO
     */
	public List<Cliente> getClientiByProvincia(RicercaProvinciaDTO ricercaProvinciaDTO) {
		return clienteRepo.findByProvinciaAllIgnoreCase(ricercaProvinciaDTO.getProvincia());
	}

	/**
	 * Associa un cliente by id
	 * @param id
     */
	public Cliente associaCliente(Long id) {
		if (clienteRepo.existsById(id)) {
			return clienteRepo.findById(id)
					.orElseThrow(() -> new NotFoundException("Customer not found"));
		} else {
			throw new NotFoundException("Cliente id " + id + " non trovato");
		}
	}

	/**
	 * RegEx per: [0]eMail, [1]numero, [2]partita Iva di un Utente
	 * @param valori
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
	 * @param customerDTO
	 * @throws WrongInsertException
	 */
	public Cliente createCustomer(CustomerDTO customerDTO) throws WrongInsertException {
		Cliente cliente;
		if (controlloDatiCliente(customerDTO.getEmail(), customerDTO.getEmailContatto(), customerDTO.getPec(),
				customerDTO.getPartitaIva(), customerDTO.getTelefono(), customerDTO.getTelefonoContatto())) {

			cliente = Cliente.builder()
//					.dataDiNascita(clienteDTO.getDataDiNascita())
					.dataInserimento(LocalDate.now())
					.dataUltimoContatto(LocalDate.now())
					.anni(Period.between(customerDTO.getDataDiNascita(), LocalDate.now()).getYears())
					.build();

			cliente.setTipologia(customerDTO.getTipologia());
			BeanUtils.copyProperties(customerDTO, cliente);
			IndirizzoLegale indirizzoLegTrovato = indirizzoLegServ.associaIndirizzoLegale(customerDTO.getIdIndirizzoLegale());
			cliente.setIndirizzoLegale(indirizzoLegTrovato);
			indirizzoLegTrovato.setCliente(cliente);
			log.info("Indirizzo Legale associato");
			IndirizzoOperativo indirizzoOpTrovato = indirizzoOpServ
					.associaIndirizzoOperativo(customerDTO.getIdIndirizzoOperativo());
			cliente.setIndirizzoOperativo(indirizzoOpTrovato);
			indirizzoOpTrovato.setCliente(cliente);
			log.info("Indirizzo Operativo associato");
			log.info("Cliente: {} {}, salvato in data: {}",
					cliente.getNomeContatto(), cliente.getCognomeContatto(), cliente.getDataInserimento());
			log.info("Customer create id {}", cliente.getId());
			return clienteRepo.save(cliente);
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
	public void updateCustomer(CustomerUpdateDTO dto) throws NotFoundException, WrongInsertException {
		if (clienteRepo.existsById(dto.getIdCliente())) {
			Cliente cliente = clienteRepo.findById(dto.getIdCliente())
					.orElseThrow(() -> new NotFoundException("Customer not found"));
			cliente.setDataUltimoContatto(LocalDate.now());
			switch (dto.getTipologia().name()) {
				case "PA": cliente.setTipologia(Tipologia.PA); break;
				case "SAS": cliente.setTipologia(Tipologia.SAS); break;
				case "SPA": cliente.setTipologia(Tipologia.SPA); break;
				case "SRL": cliente.setTipologia(Tipologia.SRL); break;
			}

			if (controlloDatiCliente(dto.getEmail(), dto.getEmailContatto(), dto.getPec(), dto.getPartitaIva(),
					dto.getTelefono(), dto.getTelefonoContatto())) {
				BeanUtils.copyProperties(dto, cliente);
			} else {
				throw new WrongInsertException("Errore inserimento dati");
			}
			log.info("Il Cliente in data {} è stato modificato", cliente.getDataUltimoContatto());
			IndirizzoLegale indirizzoLegale = indirizzoLegServ.associaIndirizzoLegale(dto.getIdIndirizzoLegale());
			cliente.setIndirizzoLegale(indirizzoLegale);
			indirizzoLegale.setCliente(cliente);
			log.info("Indirizzo Legale associato");
			IndirizzoOperativo indirizzoOptrovato = indirizzoOpServ
					.associaIndirizzoOperativo(dto.getIdIndirizzoOperativo());
			cliente.setIndirizzoOperativo(indirizzoOptrovato);
			indirizzoOptrovato.setCliente(cliente);
			log.info("Indirizzo Operativo associato");
			clienteRepo.save(cliente);
			log.info(cliente.getNomeContatto() + " " + cliente.getCognomeContatto() + " modificato");
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
			Cliente cliente = clienteRepo.findById(id).get();
			log.info("Il Cliente " + cliente.getNomeContatto() + " " + cliente.getCognomeContatto() + " in data "
					+ LocalDate.now() + " è stato eliminato");
			clienteRepo.deleteById(id);
		} else {
			throw new NotFoundException("Il Cliente id" + id + " non esiste");
		}
	}

}
