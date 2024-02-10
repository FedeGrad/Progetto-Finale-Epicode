package it.progetto.energy.service;

import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.cliente.CustomerDTO;
import it.progetto.energy.dto.cliente.CustomerUpdateDTO;
import it.progetto.energy.dto.provincia.FindProvinciaDTO;
import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.persistence.entity.Cliente;
import it.progetto.energy.persistence.entity.IndirizzoLegale;
import it.progetto.energy.persistence.entity.IndirizzoOperativo;
import it.progetto.energy.persistence.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;

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
	 * @param findProvinciaDTO
     */
	public List<Cliente> getClientiByProvincia(FindProvinciaDTO findProvinciaDTO) {
		return clienteRepo.findByProvinciaAllIgnoreCase(findProvinciaDTO.getProvincia());
	}

	/**
	 * Associa un cliente by id
	 * @param id
     */
	public Cliente associaCliente(Long id) {
		if (clienteRepo.existsById(id)) {
			return clienteRepo.findById(id)
					.orElseThrow(() -> new NotFoundException(ERROR_ONE)); //TODO
		} else {
			throw new NotFoundException(ERROR_ONE);
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
	 */
	public Cliente createCustomer(CustomerDTO customerDTO) {
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
			throw new NotCreatableException(ERROR_ONE); //TODO
		}
	}

	/**
	 * Modifica un Cliente nel sistema
	 * @param dto
	 */
	public void updateCustomer(CustomerUpdateDTO dto) {
		if (clienteRepo.existsById(dto.getIdCliente())) {
			Cliente cliente = clienteRepo.findById(dto.getIdCliente())
					.orElseThrow(() -> new NotFoundException(ERROR_ONE));

			cliente.setDataUltimoContatto(LocalDate.now());
			cliente.setTipologia(dto.getTipologia());

			if (controlloDatiCliente(dto.getEmail(), dto.getEmailContatto(), dto.getPec(), dto.getPartitaIva(),
					dto.getTelefono(), dto.getTelefonoContatto())) {
				BeanUtils.copyProperties(dto, cliente);
			} else {
				throw new NotUpdatableException(ERROR_ONE); //TODO
			}
			log.info("Il Cliente in data {} è stato modificato", cliente.getDataUltimoContatto());
			IndirizzoLegale indirizzoLegale = indirizzoLegServ.associaIndirizzoLegale(dto.getIdIndirizzoLegale());
			cliente.setIndirizzoLegale(indirizzoLegale);
			indirizzoLegale.setCliente(cliente);
			log.info("Indirizzo Legale associato");

			IndirizzoOperativo indirizzoOperativo = indirizzoOpServ
					.associaIndirizzoOperativo(dto.getIdIndirizzoOperativo());
			cliente.setIndirizzoOperativo(indirizzoOperativo);
			indirizzoOperativo.setCliente(cliente);
			log.info("Indirizzo Operativo associato");

			clienteRepo.save(cliente);
			log.info("Customer {} updated", cliente.getNomeContatto() + " " + cliente.getCognomeContatto());
		} else {
			throw new NotFoundException(ERROR_ONE); //TODO
		}
	}

	/**
	 * Elimina un cliente
	 * @param id
	 */
	public void eliminaCliente(Long id) {
		if (clienteRepo.existsById(id)) {
			Cliente cliente = clienteRepo.findById(id)
					.get();
			log.info("Customer id {} deleted in date {} ", id, LocalDate.now());
			clienteRepo.deleteById(id);
		} else {
			throw new NotFoundException(ERROR_ONE);
		}
	}

}
