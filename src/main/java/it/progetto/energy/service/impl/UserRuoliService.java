package it.progetto.energy.service.impl;

import it.progetto.energy.dto.user.UserDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.impl.model.RoleAccess;
import it.progetto.energy.impl.model.User;
import it.progetto.energy.impl.repository.RoleAccessRepository;
import it.progetto.energy.impl.repository.UserAccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
@RequiredArgsConstructor
public class UserRuoliService {

	private final UserAccessRepository userAccessRepository;
	private final RoleAccessRepository roleAccessRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * Recupera tutti gli User
	 * @deprecated
	 * @return List<User>
	 */
	@Deprecated
	public List<User> getAllUser() {
		return userAccessRepository.findAll();
	}

	/**
	 * Recupera tutti gli User per pagina
	 * @return Page<User>
	 */
	public Page<User> getAllUser(Pageable page) {
		return userAccessRepository.findAll(page);
	}

	/**
	 * Metodo per inserire un User nel sistema
	 */
	public void inserisciUser(UserDTO dto) {
		User user = new User();
		if (!userAccessRepository.existsByUsername(dto.getUsername())) {
			BeanUtils.copyProperties(dto, user);
			user.setPassword(passwordEncoder.encode(dto.getPassword()));
			user.setAccountAttivo(true);
			String elencoRuoli = dto.getRoles();
			if (elencoRuoli.isBlank()) {
				elencoRuoli = "ROLE_USER";
			}
			String[] listaRuoli = elencoRuoli.split(",");
			Set<RoleAccess> ruoli = new HashSet<RoleAccess>();
			log.info("Ruoli: " + elencoRuoli);
            for (String s : listaRuoli) {
                RoleAccess r = roleAccessRepository.findByRoleName(s);
                if (r != null) {
                    log.info(r.getRoleName().toString());
                    ruoli.add(r);
                } else {
                    throw new NotFoundException(ERROR_ONE); //TODO
                }
            }
			user.setRoles(ruoli);
			userAccessRepository.save(user);
		} else {
			throw new ElementAlreadyPresentException(ERROR_ONE);
		}
	}

	/**
	 * Metodo per modificare un User nel sistema
	 */
	public void modificaUser(UserDTO dto) {
		if (Boolean.TRUE.equals(userAccessRepository.existsByUsername(dto.getUsername()))) {
			User user = userAccessRepository.findByUsername(dto.getUsername())
					.get();
			BeanUtils.copyProperties(dto, user);
			user.setPassword(passwordEncoder.encode(dto.getPassword()));
			user.setAccountAttivo(true);
			String elencoruoli = dto.getRoles();
			if (elencoruoli.isBlank()) {
				elencoruoli = "ROLE_USER";
			}
			String[] listaRuoli = elencoruoli.split(",");
			Set<RoleAccess> ruoli = new HashSet<RoleAccess>();
			log.info("Ruoli: " + elencoruoli);
            for (String s : listaRuoli) {
                RoleAccess r = roleAccessRepository.findByRoleName(s);
                if (r != null) {
                    log.info(r.getRoleName().toString());
                    ruoli.add(r);
                } else {
                    throw new NotFoundException(ERROR_ONE);
                }
            }
			user.setRoles(ruoli);
			userAccessRepository.save(user);
		} else {
			throw new NotFoundException(ERROR_ONE);
		}
	}

	/**
	 * Metodo per eliminare un User
	 */
	public void eliminaUser(Long id) {
		if (userAccessRepository.existsById(id)) {
			userAccessRepository.deleteById(id);
			log.info("User id {} deleted", id);
		} else {
			throw new NotFoundException(ERROR_ONE);
		}
	}

}
