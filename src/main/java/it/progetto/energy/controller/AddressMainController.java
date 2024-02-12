package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.AddressMainApi;
import it.progetto.energy.dto.address.AddressDTO;
import it.progetto.energy.dto.address.AddressOutputDTO;
import it.progetto.energy.dto.address.AddressUpdateDTO;
import it.progetto.energy.mapper.dtotodomain.AddressDTOMapper;
import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.service.impl.AddressServiceImpl;
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
@RequestMapping("/main/address")
@Tag(name = "Indirizzo Legale Controller", description = "Gestione degli indirizzi legali")
@Slf4j
@RequiredArgsConstructor
public class AddressMainController implements AddressMainApi {

	private final AddressServiceImpl addressServiceImpl;
	private final AddressDTOMapper addressDTOMapper;

	@Deprecated
	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<AddressOutputDTO> findAllMainAddress() {
		List<AddressDomain> addressDomainList = addressServiceImpl.getAllIndirizziLegali();
		return addressDTOMapper.fromAddressDomainListToAddressOutputDTOList(addressDomainList);
	}

	@Override
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public List<AddressOutputDTO> findAllMainAddress(Pageable page) {
		List<AddressDomain> addressDomainList = addressServiceImpl.getAllIndirizziLegali(page);
		return addressDTOMapper.fromAddressDomainListToAddressOutputDTOList(addressDomainList);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public AddressOutputDTO createMainAddress(@Valid @RequestBody AddressDTO addressDTO) {
		AddressDomain addressDomain = addressDTOMapper.fromAddressDTOToAddressDomain(addressDTO);
		AddressDomain addressCreated = addressServiceImpl.createIndirizzo(addressDomain);
		return addressDTOMapper.fromAddressDomainToAddressOutputDTO(addressCreated);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public AddressOutputDTO updateMainAddress(@RequestBody AddressUpdateDTO addressUpdateDTO) {
		AddressDomain addressDomain = addressDTOMapper.fromAddressUpdateDTOToAddressDomain(addressUpdateDTO);
		AddressDomain addressUpdated = addressServiceImpl.updateMainAddress(addressDomain);
		return addressDTOMapper.fromAddressDomainToAddressOutputDTO(addressUpdated);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMainAddress(@PathVariable("id") Long mainAddressId) {
		addressServiceImpl.deleteMainAddress(mainAddressId);
	}

}
