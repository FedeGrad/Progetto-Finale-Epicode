package it.progetto.energy.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	public Role findByRoleName(ERole nome);
	
	@Query(value= "SELECT * FROM Roles WHERE Role_name = ?1", nativeQuery = true)
	public Role findByRoleName(String nome);
	

}
