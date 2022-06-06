package it.progetto.energy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.progetto.energy.model.IndirizzoOperativo;

public interface IndirizzoOperativoRepository extends PagingAndSortingRepository<IndirizzoOperativo, Long> {

	@Query(value = "SELECT * FROM IndirizzoOperativo "
			+ "JOIN Comune ON IndirizzoOperativo.id_comune = Comune.id "
			+ "WHERE comune.nome = ?1", nativeQuery = true)
	public Page<IndirizzoOperativo> findByComuneAllIgnoreCase(String comune, Pageable page);

	public List<IndirizzoOperativo> findByViaAllIgnoreCase(String via);
	public boolean existsByViaAllIgnoreCase(String via);

	public List<IndirizzoOperativo> findByCivico(String civico);
	public boolean existsByCivico(String civico);

	public Page<IndirizzoOperativo> findByLocalitaAllIgnoreCase(String localita, Pageable page);
	public boolean existsByLocalitaAllIgnoreCase(String localita);

	public Page<IndirizzoOperativo> findByCap(String cap, Pageable page);
	public boolean existsByCap(String cap);

}
