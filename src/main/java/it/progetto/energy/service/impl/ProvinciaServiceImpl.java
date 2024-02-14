package it.progetto.energy.service.impl;

import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotDeletableException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.mapper.entitytodomain.ProvinciaEntityMapper;
import it.progetto.energy.model.ProvinciaDomain;
import it.progetto.energy.persistence.entity.ProvinciaEntity;
import it.progetto.energy.persistence.repository.ProvinciaRepository;
import it.progetto.energy.service.ProvinciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.progetto.energy.exception.model.ErrorCodeDomain.PROVINCIA_ALREADY_PRESENT;
import static it.progetto.energy.exception.model.ErrorCodeDomain.PROVINCIA_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProvinciaServiceImpl implements ProvinciaService {

	private final ProvinciaRepository provinciaRepository;
	private final ProvinciaEntityMapper provinciaEntityMapper;

	/**
	 * Recupera tutte le Province
	 * @deprecated
	 * @return List<ProvinciaDomain>
	 */
	@Deprecated
	@Override
	public List<ProvinciaDomain> findAllProvince() {
		List<ProvinciaEntity> provinciaEntityList = provinciaRepository.findAll();
		return provinciaEntityMapper.fromProvinciaEntityListToProvinciaDomainList(provinciaEntityList);
	}

	/**
	 * Recupera tutte le Province per pagina
	 * @return List<ProvinciaDomain>
	 */
	@Override
	public List<ProvinciaDomain> findAllProvince(Pageable page) {
		List<ProvinciaEntity> provinciaEntityList = provinciaRepository.findAll(page)
				.getContent();
		return provinciaEntityMapper.fromProvinciaEntityListToProvinciaDomainList(provinciaEntityList);
	}

	/**
	 * Inserisce una Provincia
	 */
	@Override
	public ProvinciaDomain createProvincia(ProvinciaDomain provinciaDomain) {
		if (!provinciaRepository.existsBySiglaAllIgnoreCase(provinciaDomain.getSigla())) {
			ProvinciaEntity provinciaEntity = provinciaEntityMapper.fromProvinciaDomainToProvinciaEntity(provinciaDomain);
			ProvinciaEntity saved = provinciaRepository.save(provinciaEntity);
			log.info("Provincia id {} saved", saved.getId());

			return provinciaEntityMapper.fromProvinciaEntityToProvinciaDomain(saved);
		} else {
			throw new NotCreatableException(PROVINCIA_ALREADY_PRESENT);
		}
	}

	/**
	 * Modifica una Provincia
	 */
	@Override
	public ProvinciaDomain updateProvincia(ProvinciaDomain provinciaDomain) {
		ProvinciaEntity provinciaEntityToUpdate = provinciaRepository.findById(provinciaDomain.getId())
				.orElseThrow(() -> new NotUpdatableException(PROVINCIA_NOT_FOUND));
		ProvinciaEntity provinciaEntity = provinciaEntityMapper.fromProvinciaDomainToProvinciaEntity(provinciaDomain);

		//TODO MERGE TWO PROVINCIA
		ProvinciaEntity updated = provinciaRepository.save(provinciaEntityToUpdate);
		log.info("Provincia id {} updated", updated.getId());

		return provinciaEntityMapper.fromProvinciaEntityToProvinciaDomain(updated);
	}

	/**
	 * Elimina una Provincia
	 */
	@Override
	public void deleteProvincia(Long id) {
		if (provinciaRepository.existsById(id)) {
			provinciaRepository.deleteById(id);
			log.info("Provincia id {} deleted", id);
		} else {
			throw new NotDeletableException(PROVINCIA_NOT_FOUND);
		}
	}

}
