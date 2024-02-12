package it.progetto.energy.persistence.repository;

import it.progetto.energy.persistence.entity.Provincia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProvinciaRepository extends PagingAndSortingRepository<Provincia, Long> {

	@Query(value = "SELECT * " +
			"FROM Provincia " +
			"JOIN Comune " +
			"WHERE comune.nome = ?1", nativeQuery = true)
	Provincia findByComuneAllIgnoreCase(String comune);

	Provincia findByNomeAllIgnoreCase(String provincia);

	Provincia findBySiglaAllIgnoreCase(String sigla);

	boolean existsBySiglaAllIgnoreCase(String sigla);

	List<Provincia> findByRegioneAllIgnoreCase(String regione);

}
