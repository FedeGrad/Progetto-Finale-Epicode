package it.progetto.energy.persistence.repository;

import it.progetto.energy.persistence.entity.ComuneEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ComuneRepository extends PagingAndSortingRepository<ComuneEntity, Long> {

	@Query(value =
			"SELECT * " +
					"FROM Comune " +
					"JOIN Provincia ON Comune.id_provincia = Provincia.id " +
					"WHERE Provincia.name = ?1", nativeQuery = true)
	Page<ComuneEntity> findByProvinciaAllIgnoreCase(String provincia, Pageable page);

	Optional<ComuneEntity> findByNameAllIgnoreCase(String name);

	boolean existsByNameAllIgnoreCase(String nome);

	List<ComuneEntity> findByNameContainingAllIgnoreCase(String name);

	List<ComuneEntity> findByNameStartingWithAllIgnoreCase(String name);

	List<ComuneEntity> findByNameEndingWithAllIgnoreCase(String name);

	List<ComuneEntity> findByNameLikeAllIgnoreCase(String name);

}
