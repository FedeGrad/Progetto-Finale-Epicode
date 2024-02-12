package it.progetto.energy.service;

import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.mapper.entitytodomain.ComuneEntityMapper;
import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.persistence.entity.Comune;
import it.progetto.energy.persistence.entity.Provincia;
import it.progetto.energy.persistence.repository.ComuneRepository;
import it.progetto.energy.persistence.repository.ProvinciaRepository;
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
public class ComuneService {

	private final ComuneRepository comuneRepository;
	private final ProvinciaRepository provinciaRepository;
	private final ProvinciaService provinciaService;
	private final ComuneEntityMapper comuneEntityMapper;

	/**
	 * Recupera tutti i Comuni
	 */
	@Deprecated
	public List<ComuneDomain> getAllComuni() {
		List<Comune> comuneList = (List<Comune>) comuneRepository.findAll();
		return comuneEntityMapper.fromComuneListToComuneDomainList(comuneList);
	}

	/**
	 * Recupera tutti i Comuni per pagina
	 */
	public List<ComuneDomain> getAllComuni(Pageable page) {
		List<Comune> comuneList = comuneRepository.findAll(page)
				.getContent();
		return comuneEntityMapper.fromComuneListToComuneDomainList(comuneList);
	}

	/**
	 * Inserisce un Comune nel sistema
	 */
	public ComuneDomain createComune(ComuneDomain comuneDomain) throws ElementAlreadyPresentException {
		if (!comuneRepository.existsByNomeAllIgnoreCase(comuneDomain.getName())) {

			Comune comune = comuneEntityMapper.fromComuneDomainToComune(comuneDomain);
			Provincia provincia = provinciaRepository.findById(comuneDomain.getId())
					.orElseThrow(() -> new NotCreatableException(ERROR_ONE));
//			if(Objects.nonNull(provincia)){
				comune.setProvincia(provincia);
				Comune saved = comuneRepository.save(comune);
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
	public ComuneDomain updateComune(ComuneDomain comuneDomain) throws ElementAlreadyPresentException {
//		if (comuneRepository.existsById(comuneDomain.getId())) {
		Comune comune = comuneRepository.findById(comuneDomain.getId())
				.orElseThrow(() -> new NotUpdatableException(ERROR_ONE));

		if(Objects.nonNull(comuneDomain.getProvincia())){
			Provincia provincia = provinciaRepository.findBySiglaAllIgnoreCase(comuneDomain.getProvincia().getSigla());
			comune.setProvincia(provincia);
		}

		Comune updated = comuneRepository.save(comune);
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
