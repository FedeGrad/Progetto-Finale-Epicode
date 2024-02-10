package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.AddressMainApi;
import it.progetto.energy.dto.indirizzo.IndirizzoDTO;
import it.progetto.energy.dto.indirizzo.IndirizzoUpdateDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.persistence.entity.IndirizzoLegale;
import it.progetto.energy.service.IndirizzoLegaleService;
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
@RequestMapping("/main/address")
@Tag(name = "Indirizzo Legale Controller", description = "Gestione degli indirizzi legali")
@Slf4j
@RequiredArgsConstructor
public class AddressMainController implements AddressMainApi {

	private final IndirizzoLegaleService indirizzoLegServ;

	@Deprecated
	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<IndirizzoLegale> findAllMainAddress() {
		return indirizzoLegServ.getAllIndirizziLegali();
	}

	@Override
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<IndirizzoLegale> findAllMainAddress(Pageable page) {
		return indirizzoLegServ.getAllIndirizziLegali(page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void createMainAddress(@Valid @RequestBody IndirizzoDTO dto) throws ElementAlreadyPresentException {
		log.info(dto.getVia() + dto.getCap() + dto.getCivico() + dto.getLocalita());
		indirizzoLegServ.inserisciIndirizzoLegale(dto);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateMainAddress(@RequestBody IndirizzoUpdateDTO indirizzoUpdateDTO) {
		indirizzoLegServ.updateMainAddress(indirizzoUpdateDTO);
		log.info("Main address updated");
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMainAddress(@PathVariable("id") Long addressMailId) {
		indirizzoLegServ.deleteMainAddress(addressMailId);
		log.info("Main address deleted");
	}

}
