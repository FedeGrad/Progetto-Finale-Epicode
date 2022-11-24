package it.progetto.energy.impl.repository;

import it.progetto.energy.impl.model.ERoleAccess;
import it.progetto.energy.impl.model.RoleAccess;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleAccessRepository extends CrudRepository<RoleAccess, Long> {
	
	public RoleAccess findByRoleName(ERoleAccess nome);
	
	@Query(value= "SELECT * FROM Roles WHERE Role_name = ?1", nativeQuery = true)
	public RoleAccess findByRoleName(String nome);
	

}
