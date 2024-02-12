package it.progetto.energy.persistence.repository;

import it.progetto.energy.persistence.entity.Comune;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ComuneRepository extends PagingAndSortingRepository<Comune, Long> {

	@Query(value = "SELECT * " +
			"FROM Comune " +
			"JOIN Provincia ON Comune.id_provincia = Provincia.id " +
			"WHERE Provincia.nome = ?1", nativeQuery = true)
	Page<Comune> findByProvinciaAllIgnoreCase(String provincia, Pageable page);

	Optional<Comune> findByNomeAllIgnoreCase(String nome);

	boolean existsByNomeAllIgnoreCase(String nome);

	List<Comune> findByNomeContainingAllIgnoreCase(String nome);

	List<Comune> findByNomeStartingWithAllIgnoreCase(String nome);

	List<Comune> findByNomeEndingWithAllIgnoreCase(String nome);

	List<Comune> findByNomeLikeAllIgnoreCase(String nome);

}
