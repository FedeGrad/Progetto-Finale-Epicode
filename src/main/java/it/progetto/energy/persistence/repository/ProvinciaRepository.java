package it.progetto.energy.persistence.repository;

import it.progetto.energy.persistence.entity.ProvinciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinciaRepository extends JpaRepository<ProvinciaEntity, Long> {

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

//	Optional<ProvinciaEntity> findById(Long id);
//
//	List<ProvinciaEntity> findAll();
//
//	boolean existsById(Long id);
//
//	void deleteById(Long id);
}
