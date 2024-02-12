package it.progetto.energy.service;

import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.provincia.FindProvinciaDTO;
import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.mapper.entitytodomain.CustomerEntityMapper;
import it.progetto.energy.model.CustomerDomain;
import it.progetto.energy.persistence.entity.Cliente;
import it.progetto.energy.persistence.entity.IndirizzoLegale;
import it.progetto.energy.persistence.repository.ClienteRepository;
import it.progetto.energy.persistence.repository.IndirizzoLegaleRepository;
import it.progetto.energy.persistence.repository.IndirizzoOperativoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;
import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_TWO;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

	private final ClienteRepository clienteRepo;
	private final IndirizzoLegaleRepository indirizzoLegaleRepository;
	private final IndirizzoOperativoRepository indirizzoOperativoRepository;
	private final CustomerEntityMapper customerEntityMapper;

	/**
	 * Recupera tutti i Clienti
	 * @deprecated
	 */
	@Deprecated
	public List<CustomerDomain> getAllClienti() {
		List<Cliente> clienteList = clienteRepo.findAll();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(clienteList);
	}

	/**
	 * Recupera tutti i Clienti, per pagina
	 */
	public List<CustomerDomain> getAllClienti(Pageable page) {
		List<Cliente> clientePage = clienteRepo.findAll(page).getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(clientePage);
	}

	/**
	 * Recupera i Clienti per nome
	 */
	public List<CustomerDomain> getClientiByNome(String nome, Pageable page) {
		List<Cliente> clientePage = clienteRepo.findByNomeContattoAllIgnoreCase(nome, page)
				.getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(clientePage);
	}

	/**
	 * Recupera i Clienti che nel nome è presente il valore passato nel parametro, nel nome
	 */
	public List<CustomerDomain> getClientiByNomeContain(String nomeContiene, Pageable page) {
		List<Cliente> clientePage = clienteRepo.findByNomeContattoContainingAllIgnoreCase(nomeContiene, page)
				.getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(clientePage);
	}

	/**
	 * Recupera i Clienti con uno specifico fatturato
	 */
	public List<CustomerDomain> getClientiByFatturato(Double fatturato, Pageable page) {
		List<Cliente> clientePage = clienteRepo.findByFatturatoAnnuale(fatturato, page)
				.getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(clientePage);
	}

	/**
	 * Recupera i Clienti registrati in una determinata data
	 */
	public List<CustomerDomain> getClientiByDataInserimento(DataDTO dataDTO, Pageable page) {
		List<Cliente> clientePage = clienteRepo.findByDataInserimento(dataDTO.getData(), page)
				.getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(clientePage);
	}

	/**
	 * Recupera i Clienti contattati in una determinata data
	 */
	public List<CustomerDomain> getClientiByDataUltimoContatto(DataDTO dataUltContatto, Pageable page) {
		List<Cliente> clientePage = clienteRepo.findByDataUltimoContatto(dataUltContatto.getData(), page)
				.getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(clientePage);
	}

	/**
	 * Recupera i Clienti di una specifica provincia
	 */
	public List<CustomerDomain> getClientiByProvincia(FindProvinciaDTO findProvinciaDTO) {
		List<Cliente> clienteList = clienteRepo.findByProvinciaAllIgnoreCase(findProvinciaDTO.getProvincia());
		return customerEntityMapper.fromCustomerListToCustomerDomainList(clienteList);
	}

	/**
	 * Inserisce un Cliente nel sistema
	 */
	@Transactional
	public CustomerDomain createCustomer(CustomerDomain customerDomain) {
		boolean email = checkEmail(customerDomain.getEmail(), customerDomain.getCustomerEmail(), customerDomain.getPec());
		boolean phoneNumber = checkPhoneNumber(customerDomain.getCompanyPhone(), customerDomain.getCustomerPhone());
		boolean npi = checkNPI(customerDomain.getNPI());

		if (email && phoneNumber && npi) {
			Cliente cliente = customerEntityMapper.fromCustomerDomainToCustomer(customerDomain);
			IndirizzoLegale indirizzoLegTrovato = indirizzoLegaleRepository
					.findById(customerDomain.getAddressMain().getId())
					.orElse(null);
			if(Objects.nonNull(indirizzoLegTrovato)) {
				cliente.setIndirizzoLegale(indirizzoLegTrovato);
				indirizzoLegTrovato.setCliente(cliente);
				log.info("Indirizzo Legale associato");
			}

			Cliente saved = clienteRepo.save(cliente);
			//TODO ADD FHIR
			log.info("Customer create id {}", saved.getId());
			return customerEntityMapper.fromCustomerToCustomerDomain(saved);
		} else {
			throw new NotCreatableException(ERROR_ONE); //TODO errore dati errati
		}
	}

	/**
	 * Modifica un Cliente nel sistema
	 */
	@Transactional
	public CustomerDomain updateCustomer(CustomerDomain customerDomain) {
//		if (clienteRepo.existsById(customerDomain.getIdCliente())) {
		Cliente cliente = clienteRepo.findById(customerDomain.getId())
				.orElseThrow(() -> new NotFoundException(ERROR_ONE));

		boolean email = checkEmail(customerDomain.getEmail(), customerDomain.getCustomerEmail(), customerDomain.getPec());
		boolean phoneNumber = checkPhoneNumber(customerDomain.getCompanyPhone(), customerDomain.getCustomerPhone());
		boolean npi = checkNPI(customerDomain.getNPI());

		if (email && phoneNumber && npi) {
			cliente.setDataUltimoContatto(LocalDate.now());
			cliente.setTipologia(customerDomain.getType());
			BeanUtils.copyProperties(customerDomain, cliente);
			//TODO MANAGE UPDATE

			IndirizzoLegale indirizzoLegTrovato = indirizzoLegaleRepository
					.findById(customerDomain.getAddressMain().getId())
					.orElse(null);
			if(Objects.nonNull(indirizzoLegTrovato)) {
				cliente.setIndirizzoLegale(indirizzoLegTrovato);
				indirizzoLegTrovato.setCliente(cliente);
				log.info("Indirizzo Legale associato");
			}

			Cliente updated = clienteRepo.save(cliente);
			log.info("Customer updated id {}", updated.getId());
			return customerEntityMapper.fromCustomerToCustomerDomain(updated);
		} else {
			throw new NotUpdatableException(ERROR_ONE); //TODO errore dati errati
		}
//		} else {
//			throw new NotFoundException(ERROR_ONE); //TODO
//		}
	}

	/**
	 * Elimina un cliente
	 */
	public void eliminaCliente(Long id) {
		if (clienteRepo.existsById(id)) {
			clienteRepo.deleteById(id);
			log.info("Customer id {} deleted", id);
		} else {
			throw new NotFoundException(ERROR_TWO);
		}
	}

	/**
	 * RegEx per: [0]eMail, [1]numero, [2]partita Iva di un Utente
	 */

	private boolean checkEmail(String... emails) {
		return Boolean.TRUE.equals(Arrays.stream(emails)
				.allMatch(email -> email.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")));
	}

	private boolean checkPhoneNumber(String... phoneNumber) {
		return Boolean.TRUE.equals(Arrays.stream(phoneNumber)
				.allMatch(phone -> phone.matches("[\\d']{10,12}")));
	}

	private boolean checkNPI(String npi) {
		return npi.matches("[0-9]{11}");
	}

}
