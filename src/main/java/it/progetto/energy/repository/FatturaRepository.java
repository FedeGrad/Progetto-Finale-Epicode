package it.progetto.energy.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.progetto.energy.model.Fattura;
import it.progetto.energy.model.StatoFattura;

public interface FatturaRepository extends PagingAndSortingRepository<Fattura, Long> {
	
	@Query(value = "SELECT * FROM fattura "
			+ "JOIN cliente ON fattura.id_cliente = cliente.id "
			+ "WHERE cliente.id = ?1", nativeQuery = true)
	public List<Fattura> findByCliente(Long idCliente);
	
//	@Query(value = "SELECT * FROM fattura WHERE importo BETWEEN ?1 AND ?2", nativeQuery = true)
	public Page<Fattura> findByImportoBetween(Double importoMin, Double importoMax, Pageable page);
	
	public Page<Fattura> findByStatoAllIgnoreCase(StatoFattura stato, Pageable page);
	
	public Page<Fattura> findByData(LocalDate data, Pageable page);
	
	public Page<Fattura> findByAnno(Integer anno, Pageable page);
	
	public Fattura findByNumero(Integer numero);
	public boolean existsByNumero(Integer numero);
	
}
