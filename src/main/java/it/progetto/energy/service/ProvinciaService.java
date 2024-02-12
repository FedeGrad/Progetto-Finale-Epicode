package it.progetto.energy.service;

import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotDeletableException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.mapper.entitytodomain.ProvinciaEntityMapper;
import it.progetto.energy.model.ProvinciaDomain;
import it.progetto.energy.persistence.entity.Provincia;
import it.progetto.energy.persistence.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;
import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_TWO;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProvinciaService {

	private final ProvinciaRepository provinciaRepository;
	private final ProvinciaEntityMapper provinciaEntityMapper;

	/**
	 * Recupera tutte le Province
	 * @deprecated
	 * @return List<ProvinciaDomain>
	 */
	public List<ProvinciaDomain> getAllProvince() {
		List<Provincia> provinciaList = (List<Provincia>) provinciaRepository.findAll();
		return provinciaEntityMapper.fromProvinciaEntityListToProvinciaDomainList(provinciaList);
	}

	/**
	 * Recupera tutte le Province per pagina
	 * @return List<ProvinciaDomain>
	 */
	public List<ProvinciaDomain> getAllProvince(Pageable page) {
		List<Provincia> provinciaList = provinciaRepository.findAll(page)
				.getContent();
		return provinciaEntityMapper.fromProvinciaEntityListToProvinciaDomainList(provinciaList);
	}

	/**
	 * Inserisce una Provincia
	 */
	public ProvinciaDomain createProvincia(ProvinciaDomain provinciaDomain) {
		if (!provinciaRepository.existsBySiglaAllIgnoreCase(provinciaDomain.getSigla())) {
			Provincia provincia = provinciaEntityMapper.fromProvinciaDomainToProvinciaEntity(provinciaDomain);
			Provincia saved = provinciaRepository.save(provincia);
			log.info("Provincia id {} saved", saved.getId());

			return provinciaEntityMapper.fromProvinciaEntityToProvinciaDomain(saved);
		} else {
			throw new ElementAlreadyPresentException(ERROR_TWO);
		}
	}

	/**
	 * Modifica una Provincia
	 */
	public ProvinciaDomain updateProvincia(ProvinciaDomain provinciaDomain) {
//		if (provinciaRepository.existsById(provinciaDomain.getId())) {
		Provincia provinciaToUpdate = provinciaRepository.findById(provinciaDomain.getId())
				.orElseThrow(() -> new NotUpdatableException(ERROR_ONE));
		Provincia provincia = provinciaEntityMapper.fromProvinciaDomainToProvinciaEntity(provinciaDomain);

		//TODO MERGE TWO PROVINCIA
		Provincia updated = provinciaRepository.save(provinciaToUpdate);
		log.info("Provincia id {} updated", updated.getId());

		return provinciaEntityMapper.fromProvinciaEntityToProvinciaDomain(updated);
//		} else {
//			throw new NotFoundException(ERROR_ONE); //TODO
//		}
	}

	/**
	 * Elimina una Provincia
	 */
	public void deleteProvincia(Long id) {
		if (provinciaRepository.existsById(id)) {
			provinciaRepository.deleteById(id);
			log.info("Provincia id {} deleted", id);
		} else {
			throw new NotDeletableException(ERROR_ONE); //TODO
		}
	}

}
