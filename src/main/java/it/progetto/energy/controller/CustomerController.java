package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.CustomerApi;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.customer.CustomerDTO;
import it.progetto.energy.dto.customer.CustomerOutputDTO;
import it.progetto.energy.dto.customer.CustomerUpdateDTO;
import it.progetto.energy.dto.provincia.FindProvinciaDTO;
import it.progetto.energy.mapper.dtotodomain.CustomerDTOMapper;
import it.progetto.energy.model.CustomerDomain;
import it.progetto.energy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
@Tag(name = "Cliente Controller", description = "Gestione dei clienti")
@Slf4j
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

	private final CustomerService customerService;
	private final CustomerDTOMapper customerDTOMapper;

	@Deprecated
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<CustomerOutputDTO> findAllCustomer() {
		List<CustomerDomain> customerDomain = customerService.getAllClienti();
		return customerDTOMapper.fromCustomerDomainListToCustomerOutputDTOList(customerDomain);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public List<CustomerOutputDTO> findAllCustomer(Pageable page) {
		List<CustomerDomain> customerPage = customerService.getAllClienti(page);
		return customerDTOMapper.fromCustomerDomainListToCustomerOutputDTOList(customerPage);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/name/{name}/page")
	@ResponseStatus(HttpStatus.OK)
	public List<CustomerOutputDTO> findCustomerByName(@PathVariable("name") String name, Pageable page) {
		List<CustomerDomain> customerPage = customerService.getClientiByNome(name, page);
		return customerDTOMapper.fromCustomerDomainListToCustomerOutputDTOList(customerPage);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/name/contains/{name}")
	@ResponseStatus(HttpStatus.OK)
	public List<CustomerOutputDTO> findCustomerByNameContains(@PathVariable("name") String name, Pageable page) {
		List<CustomerDomain> customerPage = customerService.getClientiByNomeContain(name, page);
		return customerDTOMapper.fromCustomerDomainListToCustomerOutputDTOList(customerPage);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/data/insert")
	@ResponseStatus(HttpStatus.OK)
	public List<CustomerOutputDTO> findCustomerByDataInserimento(@RequestBody DataDTO data, Pageable page) {
		List<CustomerDomain> customerPage = customerService.getClientiByDataInserimento(data, page);
		return customerDTOMapper.fromCustomerDomainListToCustomerOutputDTOList(customerPage);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/data/last/update")
	@ResponseStatus(HttpStatus.OK)
	public List<CustomerOutputDTO> findCustomerByDataLastUpdate(@RequestBody DataDTO data, Pageable page) {
		List<CustomerDomain> customerPage = customerService.getClientiByDataUltimoContatto(data, page);
		return customerDTOMapper.fromCustomerDomainListToCustomerOutputDTOList(customerPage);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/provincia")
	@ResponseStatus(HttpStatus.OK)
	public List<CustomerOutputDTO> findCustomerByProvincia(@RequestBody FindProvinciaDTO dto) {
		List<CustomerDomain> customerList = customerService.getClientiByProvincia(dto);
		return customerDTOMapper.fromCustomerDomainListToCustomerOutputDTOList(customerList);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerOutputDTO createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
		CustomerDomain customerDomain = customerDTOMapper.fromCustomerUpdateDTOToCustomerDomain(customerDTO);
		CustomerDomain customerCreated = customerService.createCustomer(customerDomain);
		return customerDTOMapper.fromCustomerDomainToCustomerOutputDTO(customerCreated);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public CustomerOutputDTO updateCustomer(@Valid @RequestBody CustomerUpdateDTO customerUpdateDTO) {
		CustomerDomain customerDomain = customerDTOMapper.fromCustomerUpdateDTOToCustomerDomain(customerUpdateDTO);
		CustomerDomain customerUpdated = customerService.updateCustomer(customerDomain);
		return customerDTOMapper.fromCustomerDomainToCustomerOutputDTO(customerUpdated);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCustomer(@PathVariable("id") Long customerId) {
		customerService.eliminaCliente(customerId);
		log.info("Customer deleted");
	}

}
