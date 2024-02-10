package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.CustomerApi;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.cliente.CustomerDTO;
import it.progetto.energy.dto.cliente.CustomerUpdateDTO;
import it.progetto.energy.dto.provincia.RicercaProvinciaDTO;
import it.progetto.energy.exception.WrongInsertException;
import it.progetto.energy.persistence.entity.Cliente;
import it.progetto.energy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
@Tag(name = "Cliente Controller", description = "Gestione dei clienti")
@Slf4j
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

	private final CustomerService customerService;

	@Deprecated
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> findAllCustomer() {
		return customerService.getAllClienti();
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<Cliente> findAllCustomer(Pageable page) {
		//TODO ADDED MAPPPER
		return customerService.getAllClienti(page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/name/{name}/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<Cliente> findCustomerByName(@PathVariable("name") String name, Pageable page) {
		//TODO ADDED MAPPPER
		return customerService.getClientiByNome(name, page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/name/contains/{name}")
	@ResponseStatus(HttpStatus.OK)
	public Page<Cliente> findCustomerByNameContains(@PathVariable("name") String name, Pageable page) {
		//TODO ADDED MAPPPER
		return customerService.getClientiByNomeContain(name, page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/data/insert")
	@ResponseStatus(HttpStatus.OK)
	public Page<Cliente> findCustomerByDataInserimento(@RequestBody DataDTO data, Pageable page) {
		return customerService.getClientiByDataInserimento(data, page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/data/last/update")
	@ResponseStatus(HttpStatus.OK)
	public Page<Cliente> findCustomerByDataLastUpdate(@RequestBody DataDTO data, Pageable page) {
		return customerService.getClientiByDataUltimoContatto(data, page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/provincia")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> findCustomerByProvincia(@RequestBody RicercaProvinciaDTO dto) {
		return customerService.getClientiByProvincia(dto);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente createCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws WrongInsertException {
		return customerService.createCustomer(customerDTO);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateCustomer(@Valid @RequestBody CustomerUpdateDTO modificaDTO)
			throws NotFoundException, WrongInsertException {
		customerService.updateCustomer(modificaDTO);
		log.info("Customer updated");
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
