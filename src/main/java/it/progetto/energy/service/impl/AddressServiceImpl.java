package it.progetto.energy.service.impl;

import it.progetto.energy.business.UpdateMethod;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.mapper.UtilsMapper;
import it.progetto.energy.mapper.entitytodomain.AddressEntityMapper;
import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.PageDomain;
import it.progetto.energy.persistence.entity.AddressEntity;
import it.progetto.energy.persistence.entity.ComuneEntity;
import it.progetto.energy.persistence.repository.AddressRepository;
import it.progetto.energy.persistence.repository.ComuneRepository;
import it.progetto.energy.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ADDRESS_ALREADY_EXISTS;
import static it.progetto.energy.exception.model.ErrorCodeDomain.ADDRESS_NOT_FOUND;
import static it.progetto.energy.exception.model.ErrorCodeDomain.COMUNE_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

	private final AddressRepository addressRepository;
	private final ComuneRepository comuneRepository;
	private final AddressEntityMapper addressEntityMapper;
	private final UtilsMapper utilsMapper;
	private final UpdateMethod<AddressEntity> updateMethod;

	/**
	 * Recupera tutti gli Indirizzi Legali
	 * @deprecated
	 */
	@Deprecated
	public List<AddressDomain> findAllIndirizziLegali() {
		List<AddressEntity> addressEntityList = addressRepository.findAll();
		return addressEntityMapper.fromAddressEntityListToAddressDomainList(addressEntityList);
	}

	/**
	 * Recupera tutti gli Indirizzi Legali per pagina
	 */
	public List<AddressDomain> findAllAddressPaged(PageDomain pageDomain) {
		PageRequest pageRequest = utilsMapper.fromPageDomainToPageable(pageDomain);
		List<AddressEntity> addressEntityList = addressRepository.findAll(pageRequest)
				.getContent();
		return addressEntityMapper.fromAddressEntityListToAddressDomainList(addressEntityList);
	}

	/**
	 * Inserisce un Indirizzo Legale
	 */
	@Transactional
	public AddressDomain createIndirizzo(AddressDomain addressDomain) {
		if (!addressRepository.existsByWayAllIgnoreCase(addressDomain.getWay()) ||
				!addressRepository.existsByNumber(addressDomain.getNumber()) ||
				!addressRepository.existsByPostalCode(addressDomain.getPostalCode())) {

			AddressEntity addressEntity = addressEntityMapper.fromAddressDomainToAddressEntity(addressDomain);

			ComuneEntity comuneEntityTrovato = comuneRepository.findById(addressDomain.getComune().getId())
					.orElseThrow(() -> new NotCreatableException(COMUNE_NOT_FOUND));
			addressEntity.setComune(comuneEntityTrovato);
//			comuneEntityTrovato.getAddressList().add(addressEntity);

			AddressEntity saved = addressRepository.save(addressEntity);
			log.info("Main Address id {} saved", saved.getId());
			return addressEntityMapper.fromAddressEntityToAddressDomain(saved);
		} else {
			throw new ElementAlreadyPresentException(ADDRESS_ALREADY_EXISTS);
		}
	}

	/**
	 * Modifica un Indirizzo Legale
	 */
	public AddressDomain updateAddress(AddressDomain addressDomain) {
		AddressEntity oldAddress = addressRepository.findById(addressDomain.getId())
				.orElseThrow(() -> new NotUpdatableException(ADDRESS_NOT_FOUND));
		AddressEntity newAddress = addressEntityMapper.fromAddressDomainToAddressEntity(addressDomain);

		AddressEntity updatedAddress = updateMethod.updateOldEntityWithNewEntityByFields(newAddress, oldAddress);

		if(Objects.nonNull(addressDomain.getComune().getId())){
			ComuneEntity comuneEntity = comuneRepository.findById(addressDomain.getComune().getId())
					.orElseThrow(() -> new NotUpdatableException(COMUNE_NOT_FOUND));
			updatedAddress.setComune(comuneEntity);
//			comuneEntity.getAddressList().add(updatedAddress);
		}
		AddressEntity updated = addressRepository.save(updatedAddress);
		log.info("Main Address id {} updated", updated.getId());

		return addressEntityMapper.fromAddressEntityToAddressDomain(updated);
	}

	/**
	 * Elimina un Indirizzo Legale
	 */
	public void deleteAddress(Long id) {
		if (addressRepository.existsById(id)) {
			addressRepository.deleteById(id);
			log.info("Main Address id: {} deletes", id);
		} else {
			throw new NotFoundException(ADDRESS_NOT_FOUND);
		}
	}

}
