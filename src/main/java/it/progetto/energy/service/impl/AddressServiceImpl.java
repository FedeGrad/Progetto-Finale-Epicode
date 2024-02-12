package it.progetto.energy.service.impl;

import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.mapper.entitytodomain.AddressEntityMapper;
import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.persistence.entity.Comune;
import it.progetto.energy.persistence.entity.IndirizzoLegale;
import it.progetto.energy.persistence.repository.AddressRepository;
import it.progetto.energy.persistence.repository.ComuneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;
import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_TWO;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl {

	private final AddressRepository addressRepository;
	private final ComuneRepository comuneRepository;
	private final AddressEntityMapper addressEntityMapper;

	/**
	 * Recupera tutti gli Indirizzi Legali
	 * @deprecated
	 */
	@Deprecated
	public List<AddressDomain> getAllIndirizziLegali() {
		List<IndirizzoLegale> indirizzoLegaleList = (List<IndirizzoLegale>) addressRepository.findAll();
		return addressEntityMapper.fromAddressEntityListToAddressDomainList(indirizzoLegaleList);
	}

	/**
	 * Recupera tutti gli Indirizzi Legali per pagina
	 */
	public List<AddressDomain> getAllIndirizziLegali(Pageable page) {
		List<IndirizzoLegale> indirizzoLegaleList = addressRepository.findAll(page)
				.getContent();
		return addressEntityMapper.fromAddressEntityListToAddressDomainList(indirizzoLegaleList);
	}

	/**
	 * Recupera un indirizzo Legale per ID
	 */
	public AddressDomain associaIndirizzoLegale(Long id) {
		IndirizzoLegale indirizzoLegale = addressRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ERROR_ONE));
		return addressEntityMapper.fromAddressEntityToAddressDomain(indirizzoLegale);
	}

	/**
	 * Inserisce un Indirizzo Legale
	 */
	public AddressDomain createIndirizzo(AddressDomain addressDomain) throws ElementAlreadyPresentException {
		if (!addressRepository.existsByViaAllIgnoreCase(addressDomain.getWay())
				|| !addressRepository.existsByCivico(addressDomain.getNumber())
				|| !addressRepository.existsByCap(addressDomain.getPostalCode())) {

			IndirizzoLegale indirizzoLegale = addressEntityMapper.fromAddressDomainToAddressEntity(addressDomain);
			Comune comuneTrovato = comuneRepository.findById(addressDomain.getComune().getId())
					.orElseThrow(() -> new NotCreatableException(ERROR_ONE));
			indirizzoLegale.setComune(comuneTrovato);
			comuneTrovato.getIndirizziLegali().add(indirizzoLegale);

			IndirizzoLegale saved = addressRepository.save(indirizzoLegale);
			log.info("Main Address id {} saved", saved.getId());
			return addressEntityMapper.fromAddressEntityToAddressDomain(saved);
		} else {
			throw new ElementAlreadyPresentException(ERROR_TWO);
		}
	}

	/**
	 * Modifica un Indirizzo Legale
	 */
	public AddressDomain updateMainAddress(AddressDomain addressDomain) {
//		if (indirizzoLegaleRepository.existsById(addressDomain.getId())) {
		IndirizzoLegale indirizzo = addressRepository.findById(addressDomain.getId())
				.orElseThrow(() -> new NotUpdatableException(ERROR_ONE)); //TODO
		IndirizzoLegale indirizzoLegale = addressEntityMapper.fromAddressDomainToAddressEntity(addressDomain);
		//TODO MERGE INDIRIZZO CON INDIRIZZO TROVATO

		if(Objects.nonNull(addressDomain.getComune().getId())){
			Comune comune = comuneRepository.findById(addressDomain.getComune().getId())
					.orElseThrow(() -> new NotUpdatableException(ERROR_ONE));//TODO
			indirizzo.setComune(comune);
			comune.getIndirizziLegali().add(indirizzo);
		}
		IndirizzoLegale updated = addressRepository.save(indirizzo);
		log.info("Main Address id {} updated", updated.getId());
		return addressEntityMapper.fromAddressEntityToAddressDomain(updated);
//		} else {
//			throw new NotFoundException(ERROR_ONE); //TODO
//		}
	}

	/**
	 * Elimina un Indirizzo Legale
	 */
	public void deleteMainAddress(Long id) {
		if (addressRepository.existsById(id)) {
			addressRepository.deleteById(id);
			log.info("Main Address id: {} deletes", id);
		} else {
			throw new NotFoundException(ERROR_ONE); //TODO
		}
	}

}
