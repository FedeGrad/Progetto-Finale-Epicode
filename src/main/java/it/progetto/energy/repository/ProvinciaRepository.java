package it.progetto.energy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.progetto.energy.model.Provincia;

public interface ProvinciaRepository extends PagingAndSortingRepository<Provincia, Long> {

	@Query(value = "SELECT * FROM Provincia JOIN Comune WHERE comune.nome = ?1", nativeQuery = true)
	public Provincia findByComuneAllIgnoreCase(String comune);

	public Provincia findByNomeAllIgnoreCase(String provincia);
	public boolean existsByNomeAllIgnoreCase(String nome);

//	@Query(value = "SELECT * FROM Provincia WHERE sigla = ?1", nativeQuery = true)
	public Provincia findBySiglaAllIgnoreCase(String sigla);
	public boolean existsBySiglaAllIgnoreCase(String sigla);

	public List<Provincia> findByRegioneAllIgnoreCase(String regione);

}
