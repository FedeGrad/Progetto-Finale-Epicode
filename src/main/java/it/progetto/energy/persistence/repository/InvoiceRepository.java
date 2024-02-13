package it.progetto.energy.persistence.repository;

import it.progetto.energy.model.StatoFattura;
import it.progetto.energy.persistence.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceEntity, Long> {

//	@Query(value =
//			"SELECT * " +
//					"FROM invoice " +
//					"JOIN cliente ON fattura.id_cliente = cliente.id " +
//					"WHERE cliente.id = ?1", nativeQuery = true)
	List<InvoiceEntity> findByCustomer_Id(Long customerId);

	@Override
	List<InvoiceEntity> findAll();

	Page<InvoiceEntity> findByAmountBetween(Double amountMin, Double amountMax, Pageable page);

	Page<InvoiceEntity> findByStateAllIgnoreCase(StatoFattura state, Pageable page);

	Page<InvoiceEntity> findByDate(LocalDate date, Pageable page);

	Page<InvoiceEntity> findByYearContains(String year, Pageable page);

	InvoiceEntity findByNumber(Integer number);

}
