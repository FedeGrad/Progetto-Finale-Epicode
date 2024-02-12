package it.progetto.energy.service;

import it.progetto.energy.model.UserDomain;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserDomain> findAllUser();

    List<UserDomain> findAllUser(Pageable page);

    UserDomain createUser(UserDomain userDomain);

    UserDomain updateUser(UserDomain userDomain);

    void deleteUser(Long id);

}
