package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.AddressOperationalApi;
import it.progetto.energy.dto.indirizzo.IndirizzoDTO;
import it.progetto.energy.dto.indirizzo.IndirizzoModificaDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.persistence.entity.IndirizzoOperativo;
import it.progetto.energy.service.IndirizzoOperativoService;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/operational/address")
@Tag(name = "Indirizzo Operativo Controller", description = "Gestione degli indirizzi operativi")
@Slf4j
@RequiredArgsConstructor
public class AddressOperationalController implements AddressOperationalApi {

	private final IndirizzoOperativoService indirizzoOpServ;

	@Deprecated
	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<IndirizzoOperativo> findAllAddressOperational() {
		return indirizzoOpServ.getAllIndirizziOperativi();
	}

	@Override
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<IndirizzoOperativo> findAllAddressOperational(Pageable page) {
		return indirizzoOpServ.getAllIndirizziOperativi(page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void createOperationalAddress(@Valid @RequestBody IndirizzoDTO indirizzoDTO)
			throws ElementAlreadyPresentException {
		indirizzoOpServ.inserisciIndirizzoOperativo(indirizzoDTO);
		log.info("Address Operational added");
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public void updateOperationalAddress(@RequestBody IndirizzoModificaDTO indirizzoModificaDTO) {
		indirizzoOpServ.modificaIndirizzoOperativo(indirizzoModificaDTO);
		log.info("Address Operational updated");
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOperationalAddress(@PathVariable("id") Long operationalAddressId) {
		indirizzoOpServ.eliminaIndirizzoOperativo(operationalAddressId);
		log.info("Indirizzo Operativo eliminato");
	}

}
