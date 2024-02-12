package it.progetto.energy.persistence.repository;

import it.progetto.energy.persistence.entity.IndirizzoLegale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AddressRepository extends PagingAndSortingRepository<IndirizzoLegale, Long> {

	@Query(value = "SELECT * " +
			"FROM IndirizzoLegale " +
			"JOIN Comune ON IndirizzoLegale.id_comune = Comune.id " +
			"WHERE comune.nome = ?1", nativeQuery = true)
	List<IndirizzoLegale> findByComuneAllIgnoreCase(String comune, Pageable page);

	List<IndirizzoLegale> findByViaAllIgnoreCase(String via, Pageable page);

	boolean existsByViaAllIgnoreCase(String via);

	List<IndirizzoLegale> findByCivico(String civico);

	boolean existsByCivico(String civico);

	List<IndirizzoLegale> findByLocalitaAllIgnoreCase(String localita, Pageable page);

	List<IndirizzoLegale> findByCap(String cap, Pageable page);

	boolean existsByCap(String cap);

}
