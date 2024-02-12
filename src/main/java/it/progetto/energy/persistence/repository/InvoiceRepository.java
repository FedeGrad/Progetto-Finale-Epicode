package it.progetto.energy.persistence.repository;

import it.progetto.energy.model.StatoFattura;
import it.progetto.energy.persistence.entity.Fattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends PagingAndSortingRepository<Fattura, Long> {

	@Query(value = "SELECT * " +
			"FROM fattura " +
			"JOIN cliente ON fattura.id_cliente = cliente.id " +
			"WHERE cliente.id = ?1", nativeQuery = true)
	List<Fattura> findByCliente(Long idCliente);

	@Override
	List<Fattura> findAll();

	Page<Fattura> findByImportoBetween(Double importoMin, Double importoMax, Pageable page);

	Page<Fattura> findByStatoAllIgnoreCase(StatoFattura stato, Pageable page);

	Page<Fattura> findByData(LocalDate data, Pageable page);

	Page<Fattura> findByAnno(Integer anno, Pageable page);

	Fattura findByNumero(Integer numero);

}
