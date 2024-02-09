package it.progetto.energy.repository;

import java.time.LocalDate;
import java.util.List;

import it.progetto.energy.model.ClientDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClienteRepository extends PagingAndSortingRepository<ClientDomain, Long> {

	@Query(value = "SELECT * FROM cliente "
			+ "JOIN indirizzo_legale ON cliente.id = indirizzo_legale.cliente_id "
			+ "JOIN comune ON indirizzo_legale.id_comune = comune.id "
			+ "JOIN provincia ON comune.id_provincia = provincia.id "
			+ "WHERE provincia.nome = ?1", nativeQuery = true)
	public List<ClientDomain> findByProvinciaAllIgnoreCase(String provincia);
	
	public List<ClientDomain> findByRagioneSociale(String ragioneSociale);

	public Page<ClientDomain> findByNomeContattoAllIgnoreCase(String nomeContatto, Pageable page);
	public Page<ClientDomain> findByNomeContattoContainingAllIgnoreCase(String nomeContatto, Pageable page);
	public List<ClientDomain> findByNomeContattoContains(String nome);
	public List<ClientDomain> findByFatturatoAnnuale(Double fatturatoAnnuale);
	public Page<ClientDomain> findByFatturatoAnnuale(Double fatturatoAnnuale, Pageable page);
	
	public Page<ClientDomain> findByDataInserimento(LocalDate dataInserimento, Pageable page);

	public Page<ClientDomain> findByDataUltimoContatto(LocalDate dataUltimoContatto, Pageable page);

	public ClientDomain findByEmailAllIgnoreCase(String email);
	public boolean existsByEmailAllIgnoreCase(String email);

	public ClientDomain findByEmailContattoAllIgnoreCase(String emailContatto);
	public boolean existsByEmailContattoAllIgnoreCase(String emailContatto);

	public ClientDomain findByPecAllIgnoreCase(String pec);
	public boolean existsByPecAllIgnoreCase(String pec);

	public ClientDomain findByTelefonoContattoAllIgnoreCase(String telefonoContatto);
	public boolean existsByTelefonoContattoAllIgnoreCase(String telefonoContatto);

}
