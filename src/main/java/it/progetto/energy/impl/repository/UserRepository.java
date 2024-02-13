package it.progetto.energy.impl.repository;

import it.progetto.energy.impl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameIgnoreCase(String username);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Long deleteByUsername(String username);
}