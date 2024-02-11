package it.progetto.energy.persistence.repository;

import it.progetto.energy.persistence.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long> {

	@Query(value =
			"SELECT * FROM cliente "
			+ "JOIN indirizzo_legale ON cliente.id = indirizzo_legale.cliente_id "
			+ "JOIN comune ON indirizzo_legale.id_comune = comune.id "
			+ "JOIN provincia ON comune.id_provincia = provincia.id "
			+ "WHERE provincia.nome = ?1"
			, nativeQuery = true)
	List<Cliente> findByProvinciaAllIgnoreCase(String provincia);

	@Override
	List<Cliente> findAll();

	public Page<Cliente> findByNomeContattoAllIgnoreCase(String nomeContatto, Pageable page);
	public Page<Cliente> findByNomeContattoContainingAllIgnoreCase(String nomeContatto, Pageable page);
	public List<Cliente> findByNomeContattoContains(String nome);
	public List<Cliente> findByFatturatoAnnuale(Double fatturatoAnnuale);
	public Page<Cliente> findByFatturatoAnnuale(Double fatturatoAnnuale, Pageable page);

	public Page<Cliente> findByDataInserimento(LocalDate dataInserimento, Pageable page);

	public Page<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable page);

	public Cliente findByEmailAllIgnoreCase(String email);
	public boolean existsByEmailAllIgnoreCase(String email);

	public Cliente findByEmailContattoAllIgnoreCase(String emailContatto);
	public boolean existsByEmailContattoAllIgnoreCase(String emailContatto);

	public Cliente findByPecAllIgnoreCase(String pec);
	public boolean existsByPecAllIgnoreCase(String pec);

	public Cliente findByTelefonoContattoAllIgnoreCase(String telefonoContatto);
	public boolean existsByTelefonoContattoAllIgnoreCase(String telefonoContatto);

}
