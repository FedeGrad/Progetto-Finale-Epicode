package it.progetto.energy.service.impl;

import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.mapper.entitytodomain.AddressEntityMapper;
import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.persistence.entity.AddressEntity;
import it.progetto.energy.persistence.entity.ComuneEntity;
import it.progetto.energy.persistence.repository.AddressRepository;
import it.progetto.energy.persistence.repository.ComuneRepository;
import it.progetto.energy.service.AddressService;
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
public class AddressServiceImpl implements AddressService {

	private final AddressRepository addressRepository;
	private final ComuneRepository comuneRepository;
	private final AddressEntityMapper addressEntityMapper;

	/**
	 * Recupera tutti gli Indirizzi Legali
	 * @deprecated
	 */
	@Deprecated
	public List<AddressDomain> findAllIndirizziLegali() {
		List<AddressEntity> addressEntityList = (List<AddressEntity>) addressRepository.findAll();
		return addressEntityMapper.fromAddressEntityListToAddressDomainList(addressEntityList);
	}

	/**
	 * Recupera tutti gli Indirizzi Legali per pagina
	 */
	public List<AddressDomain> findAllIndirizziLegali(Pageable page) {
		List<AddressEntity> addressEntityList = addressRepository.findAll(page)
				.getContent();
		return addressEntityMapper.fromAddressEntityListToAddressDomainList(addressEntityList);
	}

	/**
	 * Inserisce un Indirizzo Legale
	 */
	public AddressDomain createIndirizzo(AddressDomain addressDomain) {
		if (!addressRepository.existsByWayAllIgnoreCase(addressDomain.getWay()) ||
				!addressRepository.existsByNumber(addressDomain.getNumber()) ||
				!addressRepository.existsByPostalCode(addressDomain.getPostalCode())) {

			AddressEntity addressEntity = addressEntityMapper.fromAddressDomainToAddressEntity(addressDomain);

			ComuneEntity comuneEntityTrovato = comuneRepository.findById(addressDomain.getComune().getId())
					.orElseThrow(() -> new NotCreatableException(ERROR_ONE));
			addressEntity.setComune(comuneEntityTrovato);
			comuneEntityTrovato.getAddressList().add(addressEntity);

			AddressEntity saved = addressRepository.save(addressEntity);
			log.info("Main Address id {} saved", saved.getId());
			return addressEntityMapper.fromAddressEntityToAddressDomain(saved);
		} else {
			throw new ElementAlreadyPresentException(ERROR_TWO);
		}
	}

	/**
	 * Modifica un Indirizzo Legale
	 */
	public AddressDomain updateAddress(AddressDomain addressDomain) {
//		if (indirizzoLegaleRepository.existsById(addressDomain.getId())) {
		AddressEntity indirizzo = addressRepository.findById(addressDomain.getId())
				.orElseThrow(() -> new NotUpdatableException(ERROR_ONE)); //TODO
		AddressEntity addressEntity = addressEntityMapper.fromAddressDomainToAddressEntity(addressDomain);
		//TODO MERGE INDIRIZZO CON INDIRIZZO TROVATO

		if(Objects.nonNull(addressDomain.getComune().getId())){
			ComuneEntity comuneEntity = comuneRepository.findById(addressDomain.getComune().getId())
					.orElseThrow(() -> new NotUpdatableException(ERROR_ONE));//TODO
			indirizzo.setComune(comuneEntity);
			comuneEntity.getAddressList().add(indirizzo);
		}
		AddressEntity updated = addressRepository.save(indirizzo);
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
