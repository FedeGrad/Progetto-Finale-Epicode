package it.progetto.energy.service;

import it.progetto.energy.dto.user.UserDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.impl.model.RoleAccess;
import it.progetto.energy.impl.model.User;
import it.progetto.energy.impl.repository.RoleAccessRepository;
import it.progetto.energy.impl.repository.UserAccessRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;

@Service
@Slf4j
public class UserRuoliService {

	@Autowired
	UserAccessRepository userRepo;
	@Autowired
	RoleAccessRepository roleRepo;
	@Autowired
	PasswordEncoder passEnc;

	/**
	 * Recupera tutti gli User
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	/**
	 * Recupera tutti gli User per pagina
	 * @param page
	 * @return
	 */
	public Page<User> getAllUser(Pageable page) {
		return userRepo.findAll(page);
	}

	/**
	 * Metodo per inserire un User nel sistema
	 * @param dto
	 * @throws ElementAlreadyPresentException
	 */
	public void inserisciUser(UserDTO dto) throws ElementAlreadyPresentException {
		User user = new User();
		if (!userRepo.existsByUsername(dto.getUsername())) {
			BeanUtils.copyProperties(dto, user);
			user.setPassword(passEnc.encode(dto.getPassword()));
			user.setAccountAttivo(true);
			String elencoRuoli = dto.getRoles();
			if (elencoRuoli.isBlank()) {
				elencoRuoli = "ROLE_USER";
			}
			String[] listaRuoli = elencoRuoli.split(",");
			Set<RoleAccess> ruoli = new HashSet<RoleAccess>();
			log.info("Ruoli: " + elencoRuoli);
            for (String s : listaRuoli) {
                RoleAccess r = roleRepo.findByRoleName(s);
                if (r != null) {
                    log.info(r.getRoleName().toString());
                    ruoli.add(r);
                } else {
                    throw new NotFoundException(ERROR_ONE); //TODO
                }
            }
			user.setRoles(ruoli);
			userRepo.save(user);
		} else {
			throw new ElementAlreadyPresentException(ERROR_ONE);
		}
	}

	/**
	 * Metodo per modificare un User nel sistema
	 * @param dto
	 * @throws NotFoundException
	 */
	public void modificaUser(UserDTO dto) throws NotFoundException {
		if (Boolean.TRUE.equals(userRepo.existsByUsername(dto.getUsername()))) {
			User user = userRepo.findByUsername(dto.getUsername())
					.get();
			BeanUtils.copyProperties(dto, user);
			user.setPassword(passEnc.encode(dto.getPassword()));
			user.setAccountAttivo(true);
			String elencoruoli = dto.getRoles();
			if (elencoruoli.isBlank()) {
				elencoruoli = "ROLE_USER";
			}
			String[] listaRuoli = elencoruoli.split(",");
			Set<RoleAccess> ruoli = new HashSet<RoleAccess>();
			log.info("Ruoli: " + elencoruoli);
            for (String s : listaRuoli) {
                RoleAccess r = roleRepo.findByRoleName(s);
                if (r != null) {
                    log.info(r.getRoleName().toString());
                    ruoli.add(r);
                } else {
                    throw new NotFoundException(ERROR_ONE);
                }
            }
			user.setRoles(ruoli);
			userRepo.save(user);
		} else {
			throw new NotFoundException(ERROR_ONE);
		}
	}

	/**
	 * Metodo per eliminare un User
	 * @param id
	 */
	public void eliminaUser(Long id) {
		if (userRepo.existsById(id)) {
			User user = userRepo.findById(id)
					.get();
			log.info(user + " Eliminato");
			userRepo.deleteById(id);
		} else {
			throw new NotFoundException(ERROR_ONE);
		}
	}

}
