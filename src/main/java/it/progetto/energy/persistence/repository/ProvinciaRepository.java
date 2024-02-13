package it.progetto.energy.persistence.repository;

import it.progetto.energy.persistence.entity.ProvinciaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProvinciaRepository extends PagingAndSortingRepository<ProvinciaEntity, Long> {

	@Query(value =
			"SELECT * " +
					"FROM Provincia " +
					"JOIN Comune " +
					"WHERE comune.name = ?1", nativeQuery = true)
	ProvinciaEntity findByComuneAllIgnoreCase(String comuneName);

	ProvinciaEntity findByNameAllIgnoreCase(String name);

	ProvinciaEntity findBySiglaAllIgnoreCase(String sigla);

	boolean existsBySiglaAllIgnoreCase(String sigla);

	List<ProvinciaEntity> findByRegionAllIgnoreCase(String region);

}
