package it.progetto.energy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.progetto.energy.model.Comune;

public interface ComuneRepository extends PagingAndSortingRepository<Comune, Long> {

	@Query(value = "SELECT * FROM Comune "
			+ "JOIN Provincia ON Comune.id_provincia = Provincia.id "
			+ "WHERE Provincia.nome = ?1", nativeQuery = true)
	public Page<Comune> findByProvinciaAllIgnoreCase(String provincia, Pageable page);

	public Comune findByNomeAllIgnoreCase(String nome);
	public boolean existsByNomeAllIgnoreCase(String nome);

	public List<Comune> findByNomeContainingAllIgnoreCase(String nome);

	public List<Comune> findByNomeStartingWithAllIgnoreCase(String nome);

	public List<Comune> findByNomeEndingWithAllIgnoreCase(String nome);

	public List<Comune> findByNomeLikeAllIgnoreCase(String nome);
}
