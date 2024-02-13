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

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;

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
					.orElseThrow(() -> new NotCreatableException(ERROR_ONE));
//			if(Objects.nonNull(provincia)){
				comuneEntity.setProvincia(provinciaEntity);
				ComuneEntity saved = comuneRepository.save(comuneEntity);
				log.info("Comune id {} saved", saved.getId());
				return comuneEntityMapper.fromComuneToComuneDomain(saved);
//			} else {
//				throw new NotCreatableException(ERROR_ONE);
//			}
		} else {
			throw new NotCreatableException(ERROR_ONE); //TODO REFACT comune already exists
		}
	}

	/**
	 * Modifica un Comune nel sistema
	 */
	public ComuneDomain updateComune(ComuneDomain comuneDomain) {
//		if (comuneRepository.existsById(comuneDomain.getId())) {
		ComuneEntity comuneEntity = comuneRepository.findById(comuneDomain.getId())
				.orElseThrow(() -> new NotUpdatableException(ERROR_ONE));

		if(Objects.nonNull(comuneDomain.getProvincia())){
			ProvinciaEntity provinciaEntity = provinciaRepository.findBySiglaAllIgnoreCase(comuneDomain.getProvincia().getSigla());
			comuneEntity.setProvincia(provinciaEntity);
		}

		ComuneEntity updated = comuneRepository.save(comuneEntity);
		log.info("Comune id {} it was updated ", updated.getId());
		return comuneEntityMapper.fromComuneToComuneDomain(updated);
//		} else {
//			throw new NotFoundException("Il Comune con l'id " + dto.getIdComune() + " non è presente nel sistema");
//		}
	}

	/**
	 * Elimina un Comune dal sistema
	 */
	public void deleteComune(Long id) {
		if (comuneRepository.existsById(id)) {
			comuneRepository.deleteById(id);
			log.info("Comune id {} deleted", id);
		} else {
			throw new NotFoundException(ERROR_ONE); //TODO
		}
	}

}
