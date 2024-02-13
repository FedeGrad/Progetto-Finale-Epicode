package it.progetto.energy.persistence.repository;

import it.progetto.energy.model.StatoFattura;
import it.progetto.energy.persistence.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

	List<InvoiceEntity> findByCustomer_Id(Long customerId);

//	List<InvoiceEntity> findAll();

	Page<InvoiceEntity> findByAmountBetween(Double amountMin, Double amountMax, Pageable page);

	Page<InvoiceEntity> findByStateAllIgnoreCase(StatoFattura state, Pageable page);

	Page<InvoiceEntity> findByDate(LocalDate date, Pageable page);

	Page<InvoiceEntity> findByYearContains(String year, Pageable page);

	InvoiceEntity findByNumber(Integer number);

//	Optional<InvoiceEntity> findById(Long id);
//
//	boolean existsById(Long id);
//
//	void deleteById(Long id);
}
