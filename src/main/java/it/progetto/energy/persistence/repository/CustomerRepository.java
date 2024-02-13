package it.progetto.energy.persistence.repository;

import it.progetto.energy.persistence.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity, Long> {

	@Query(value =
			"SELECT * " +
					"FROM Customer " +
					"JOIN Address ON Customer.id = Address.customer_id " +
					"JOIN Comune ON Address.id_comune = Comune.id " +
					"JOIN provincia ON comune.id_provincia = provincia.id " +
					"WHERE Provincia.id = ?1", nativeQuery = true)
	List<CustomerEntity> findByProvincia_IdAllIgnoreCase(Long provinciaId);

	@Override
	List<CustomerEntity> findAll();

	Page<CustomerEntity> findByNameAllIgnoreCase(String name, Pageable page);

	Page<CustomerEntity> findByNameContainingAllIgnoreCase(String name, Pageable page);

	List<CustomerEntity> findByNameContains(String name);

	List<CustomerEntity> findByAnnualTurnover(Double annualTurnover);

	Page<CustomerEntity> findByAnnualTurnover(Double annualTurnover, Pageable page);

	Page<CustomerEntity> findByDataCreate(LocalDate dataCreate, Pageable page);

	Page<CustomerEntity> findByDataLastUpdate(LocalDate dataLastUpdate, Pageable page);

	CustomerEntity findByEmailAllIgnoreCase(String email);

	CustomerEntity findByCustomerEmailAllIgnoreCase(String customerEmail);

	CustomerEntity findByPecAllIgnoreCase(String pec);

	CustomerEntity findByCustomerPhoneAllIgnoreCase(String customerPhone);

}
