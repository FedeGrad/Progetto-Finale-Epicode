package it.progetto.energy.impl;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	public Boolean existsByUsername(String username);
	public Long deleteByUsername(String username);
	
}
