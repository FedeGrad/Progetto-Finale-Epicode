package it.progetto.energy.persistence.repository;

import it.progetto.energy.persistence.entity.AddressEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AddressRepository extends PagingAndSortingRepository<AddressEntity, Long> {

	@Query(value =
			"SELECT * " +
					"FROM Address " +
					"JOIN Comune ON Address.id_comune = Comune.id " +
					"WHERE comune.name = ?1", nativeQuery = true)
	List<AddressEntity> findByComuneAllIgnoreCase(String comuneName, Pageable page);

	List<AddressEntity> findByWayAllIgnoreCase(String way, Pageable page);

	boolean existsByWayAllIgnoreCase(String way);

	List<AddressEntity> findByNumber(String number);

	boolean existsByNumber(String civico);

	List<AddressEntity> findByLocationAllIgnoreCase(String location, Pageable page);

	List<AddressEntity> findByPostalCode(String cap, Pageable page);

	boolean existsByPostalCode(String cap);

}
