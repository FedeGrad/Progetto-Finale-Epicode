package it.progetto.energy.impl.repository;

import java.util.Optional;

import it.progetto.energy.impl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	public Boolean existsByUsername(String username);
	public Long deleteByUsername(String username);
	
}
