package it.progetto.energy.persistence.repository;

import it.progetto.energy.persistence.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Cliente, Long> {

	@Query(value =
			"SELECT * FROM cliente "
					+ "JOIN indirizzo_legale ON cliente.id = indirizzo_legale.cliente_id "
					+ "JOIN comune ON indirizzo_legale.id_comune = comune.id "
					+ "JOIN provincia ON comune.id_provincia = provincia.id "
					+ "WHERE provincia.id = ?1", nativeQuery = true)
	List<Cliente> findByProvincia_IdAllIgnoreCase(Long provinciaId);

	@Override
	List<Cliente> findAll();

	Page<Cliente> findByNomeContattoAllIgnoreCase(String nomeContatto, Pageable page);

	Page<Cliente> findByNomeContattoContainingAllIgnoreCase(String nomeContatto, Pageable page);

	List<Cliente> findByNomeContattoContains(String nome);

	List<Cliente> findByFatturatoAnnuale(Double fatturatoAnnuale);

	Page<Cliente> findByFatturatoAnnuale(Double fatturatoAnnuale, Pageable page);

	Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable page);

	Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable page);

	Cliente findByEmailAllIgnoreCase(String email);

	Cliente findByEmailContattoAllIgnoreCase(String emailContatto);

	Cliente findByPecAllIgnoreCase(String pec);

	Cliente findByTelefonoContattoAllIgnoreCase(String telefonoContatto);

}
