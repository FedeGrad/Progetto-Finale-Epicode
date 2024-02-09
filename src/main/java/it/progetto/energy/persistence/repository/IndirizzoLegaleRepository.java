package it.progetto.energy.persistence.repository;

import it.progetto.energy.persistence.entity.IndirizzoLegale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IndirizzoLegaleRepository extends PagingAndSortingRepository<IndirizzoLegale, Long> {

	@Query(value = "SELECT * FROM IndirizzoLegale "
			+ "JOIN Comune ON IndirizzoLegale.id_comune = Comune.id "
			+ "WHERE comune.nome = ?1", nativeQuery = true)
	public List<IndirizzoLegale> findByComuneAllIgnoreCase(String comune, Pageable page);

	public List<IndirizzoLegale> findByViaAllIgnoreCase(String via, Pageable page);
	public boolean existsByViaAllIgnoreCase(String via);

	public List<IndirizzoLegale> findByCivico(String civico);
	public boolean existsByCivico(String civico);

	public List<IndirizzoLegale> findByLocalitaAllIgnoreCase(String localita, Pageable page);
	public boolean existsByLocalitaAllIgnoreCase(String localita);

	public List<IndirizzoLegale> findByCap(String cap, Pageable page);
	public boolean existsByCap(String cap);

}
