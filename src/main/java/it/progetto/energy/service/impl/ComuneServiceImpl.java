package it.progetto.energy.service.impl;

import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.mapper.entitytodomain.ComuneEntityMapper;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.persistence.entity.ComuneEntity;
import it.progetto.energy.persistence.entity.ProvinciaEntity;
import it.progetto.energy.persistence.repository.ComuneRepository;
import it.progetto.energy.persistence.repository.ProvinciaRepository;
import it.progetto.energy.service.ComuneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static it.progetto.energy.exception.model.ErrorCodeDomain.COMUNE_ALREADY_EXISTS;
import static it.progetto.energy.exception.model.ErrorCodeDomain.COMUNE_NOT_FOUND;
import static it.progetto.energy.exception.model.ErrorCodeDomain.PROVINCIA_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComuneServiceImpl implements ComuneService {

	private final ComuneRepository comuneRepository;
	private final ProvinciaRepository provinciaRepository;
	private final ComuneEntityMapper comuneEntityMapper;

	/**
	 * Recupera tutti i Comuni
	 */
	@Deprecated
	public List<ComuneDomain> findAllComuni() {
		List<ComuneEntity> comuneEntityList = comuneRepository.findAll();
		return comuneEntityMapper.fromComuneListToComuneDomainList(comuneEntityList);
	}

	/**
	 * Recupera tutti i Comuni per pagina
	 */
	public List<ComuneDomain> findAllComuni(Pageable page) {
		List<ComuneEntity> comuneEntityList = comuneRepository.findAll(page)
				.getContent();
		return comuneEntityMapper.fromComuneListToComuneDomainList(comuneEntityList);
	}

	/**
	 * Inserisce un Comune nel sistema
	 */
	public ComuneDomain createComune(ComuneDomain comuneDomain) {
		if (!comuneRepository.existsByNameAllIgnoreCase(comuneDomain.getName())) {

			ComuneEntity comuneEntity = comuneEntityMapper.fromComuneDomainToComune(comuneDomain);
			ProvinciaEntity provinciaEntity = provinciaRepository.findById(comuneDomain.getProvincia().getId())
					.orElseThrow(() -> new NotCreatableException(PROVINCIA_NOT_FOUND));

			comuneEntity.setProvincia(provinciaEntity);
			ComuneEntity saved = comuneRepository.save(comuneEntity);
			log.info("Comune id {} saved", saved.getId());

			return comuneEntityMapper.fromComuneToComuneDomain(saved);
		} else {
			throw new NotCreatableException(COMUNE_ALREADY_EXISTS);
		}
	}

	/**
	 * Modifica un Comune nel sistema
	 */
	public ComuneDomain updateComune(ComuneDomain comuneDomain) {
		ComuneEntity comuneEntity = comuneRepository.findById(comuneDomain.getId())
				.orElseThrow(() -> new NotUpdatableException(COMUNE_NOT_FOUND));

		if(Objects.nonNull(comuneDomain.getProvincia())){
			ProvinciaEntity provinciaEntity = provinciaRepository.findBySiglaAllIgnoreCase(comuneDomain.getProvincia().getSigla())
					.stream()
					.findFirst()
					.orElse(null);
			comuneEntity.setProvincia(provinciaEntity);
		}
		ComuneEntity updated = comuneRepository.save(comuneEntity);
		log.info("Comune id {} it was updated ", updated.getId());

		return comuneEntityMapper.fromComuneToComuneDomain(updated);
	}

	/**
	 * Elimina un Comune dal sistema
	 */
	public void deleteComune(Long id) {
		if (comuneRepository.existsById(id)) {
			comuneRepository.deleteById(id);
			log.info("Comune id {} deleted", id);
		} else {
			throw new NotFoundException(COMUNE_NOT_FOUND);
		}
	}

}
