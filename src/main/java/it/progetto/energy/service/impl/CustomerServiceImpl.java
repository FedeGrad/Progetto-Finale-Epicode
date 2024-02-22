package it.progetto.energy.service.impl;

import it.progetto.energy.business.UpdateMethod;
import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.mapper.UtilsMapper;
import it.progetto.energy.mapper.entitytodomain.CustomerEntityMapper;
import it.progetto.energy.model.CustomerDomain;
import it.progetto.energy.model.DataDomain;
import it.progetto.energy.model.PageDomain;
import it.progetto.energy.persistence.entity.AddressEntity;
import it.progetto.energy.persistence.entity.CustomerEntity;
import it.progetto.energy.persistence.repository.AddressRepository;
import it.progetto.energy.persistence.repository.CustomerRepository;
import it.progetto.energy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static it.progetto.energy.exception.model.ErrorCodeDomain.COMSTOMER_ALREADY_EXISTS;
import static it.progetto.energy.exception.model.ErrorCodeDomain.CUSTOMER_NOT_FOUND;
import static it.progetto.energy.exception.model.ErrorCodeDomain.INVALID_IMPUT_VALUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final AddressRepository addressRepository;
	private final CustomerEntityMapper customerEntityMapper;
	private final UtilsMapper utilsMapper;
	private final UpdateMethod<CustomerEntity> updateMethod;

	/**
	 * Recupera tutti i Clienti
	 * @deprecated
	 */
	@Deprecated
	public List<CustomerDomain> findAllCustomer() {
		List<CustomerEntity> customerEntityList = customerRepository.findAll();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(customerEntityList);
	}

	/**
	 * Recupera tutti i Clienti, per pagina
	 */
	public List<CustomerDomain> findAllCustomerPaged(PageDomain pageDomain) {
		PageRequest page = utilsMapper.fromPageDomainToPageable(pageDomain);
		List<CustomerEntity> customerEntityPage = customerRepository.findAll(page).getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(customerEntityPage);
	}

	/**
	 * Recupera i Clienti per nome
	 */
	public List<CustomerDomain> findCustomerByName(String name, PageDomain pageDomain) {
		PageRequest page = utilsMapper.fromPageDomainToPageable(pageDomain);
		List<CustomerEntity> customerEntityPage = customerRepository.findByNameAllIgnoreCase(name, page)
				.getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(customerEntityPage);
	}

	/**
	 * Recupera i Clienti che nel nome è presente il valore passato nel parametro, nel nome
	 */
	public List<CustomerDomain> findCustomerByNameContain(String nomeContiene, PageDomain pageDomain) {
		PageRequest page = utilsMapper.fromPageDomainToPageable(pageDomain);
		List<CustomerEntity> customerEntityPage = customerRepository.findByNameContainingAllIgnoreCase(nomeContiene, page)
				.getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(customerEntityPage);
	}

	/**
	 * Recupera i Clienti con uno specifico fatturato
	 */
	public List<CustomerDomain> findCustomerByAnnualTurnover(Double annualTurnover, PageDomain pageDomain) {
		PageRequest page = utilsMapper.fromPageDomainToPageable(pageDomain);
		List<CustomerEntity> customerEntityPage = customerRepository.findByAnnualTurnover(annualTurnover, page)
				.getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(customerEntityPage);
	}

	/**
	 * Recupera i Clienti registrati in una determinata data
	 */
	public List<CustomerDomain> findCustomerByDataCreate(DataDomain dataCreate, PageDomain pageDomain) {
		PageRequest page = utilsMapper.fromPageDomainToPageable(pageDomain);
		List<CustomerEntity> customerEntityPage = customerRepository.findByDataCreate(dataCreate.getData(), page)
				.getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(customerEntityPage);
	}

	/**
	 * Recupera i Clienti contattati in una determinata data
	 */
	public List<CustomerDomain> findCustomerByDataLastUpdate(DataDomain dataLastUpdate, PageDomain pageDomain) {
		PageRequest page = utilsMapper.fromPageDomainToPageable(pageDomain);
		List<CustomerEntity> customerEntityPage = customerRepository.findByDataLastUpdate(dataLastUpdate.getData(), page)
				.getContent();
		return customerEntityMapper.fromCustomerListToCustomerDomainList(customerEntityPage);
	}

	/**
	 * Recupera i Clienti di una specifica provincia
	 */
	public List<CustomerDomain> findCustomerByProvincia(Long provinciaId) {
		List<CustomerEntity> customerEntityList = customerRepository.findByProvincia_IdAllIgnoreCase(provinciaId);
		return customerEntityMapper.fromCustomerListToCustomerDomainList(customerEntityList);
	}

	/**
	 * Inserisce un Cliente nel sistema
	 */
	@Transactional
	public CustomerDomain createCustomer(CustomerDomain customerDomain) {
		boolean exists = customerRepository.existsByNpi(customerDomain.getNpi());
		if(exists){
			throw new NotCreatableException(COMSTOMER_ALREADY_EXISTS);
		}

		boolean email = checkEmail(customerDomain.getEmail(), customerDomain.getCustomerEmail(), customerDomain.getPec());
		boolean phoneNumber = checkPhoneNumber(customerDomain.getCompanyPhone(), customerDomain.getCustomerPhone());
		boolean npi = checkNPI(customerDomain.getNpi());

		if (email && phoneNumber && npi) {
			CustomerEntity customerEntity = customerEntityMapper.fromCustomerDomainToCustomer(customerDomain);
			AddressEntity addressEntity = addressRepository
					.findById(customerDomain.getAddress().getId())
					.orElse(null);
			if(Objects.nonNull(addressEntity)) {
				customerEntity.setAddress(addressEntity);
//				addressEntity.setCustomer(customerEntity);
				log.info("Address joined");
			}

			CustomerEntity saved = customerRepository.save(customerEntity);
			//TODO ADD FHIR
			log.info("Customer create id {}", saved.getId());
			return customerEntityMapper.fromCustomerToCustomerDomain(saved);
		} else {
			throw new NotCreatableException(INVALID_IMPUT_VALUE);
		}
	}

	/**
	 * Modifica un Cliente nel sistema
	 */
	@Transactional
	public CustomerDomain updateCustomer(CustomerDomain customerDomain) {
		boolean email = checkEmail(customerDomain.getEmail(), customerDomain.getCustomerEmail(), customerDomain.getPec());
		boolean phoneNumber = checkPhoneNumber(customerDomain.getCompanyPhone(), customerDomain.getCustomerPhone());
		boolean npi = checkNPI(customerDomain.getNpi());

		if (email && phoneNumber && npi) {
			CustomerEntity oldCustomer = customerRepository.findById(customerDomain.getId())
					.orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND));
			CustomerEntity newCustomer = customerEntityMapper.fromCustomerDomainToCustomer(customerDomain);

			CustomerEntity customerUpdated = updateMethod.updateOldEntityWithNewEntityByMethod(newCustomer, oldCustomer);

			customerUpdated.setDataLastUpdate(LocalDate.now());
			customerUpdated.setType(customerDomain.getType());

			AddressEntity addressEntity = addressRepository
					.findById(customerDomain.getAddress().getId())
					.orElse(null);
			if(Objects.nonNull(addressEntity)) {
				customerUpdated.setAddress(addressEntity);
//				addressEntity.setCustomer(customerEntity);
				log.info("Address joined");
			}

			CustomerEntity updated = customerRepository.save(oldCustomer);
			log.info("Customer updated id {}", updated.getId());
			return customerEntityMapper.fromCustomerToCustomerDomain(updated);
		} else {
			throw new NotUpdatableException(INVALID_IMPUT_VALUE);
		}
	}

	/**
	 * Elimina un cliente
	 */
	public void deleteCustomer(Long id) {
		if (customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
			log.info("Customer id {} deleted", id);
		} else {
			throw new NotFoundException(CUSTOMER_NOT_FOUND);
		}
	}

	private boolean checkEmail(String... emails) {
		return Boolean.TRUE.equals(Arrays.stream(emails)
				.allMatch(email -> email.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")));
	}

	private boolean checkPhoneNumber(String... phoneNumber) {
		return Boolean.TRUE.equals(Arrays.stream(phoneNumber)
				.allMatch(phone -> phone.matches("[\\d']{10,12}")));
	}

	private boolean checkNPI(String npi) {
		return npi.matches("[\\d']{11}");
	}

}