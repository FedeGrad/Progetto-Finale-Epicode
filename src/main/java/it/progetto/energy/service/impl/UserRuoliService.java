package it.progetto.energy.service.impl;

import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.impl.model.RoleAccess;
import it.progetto.energy.impl.model.User;
import it.progetto.energy.impl.repository.RoleAccessRepository;
import it.progetto.energy.impl.repository.UserAccessRepository;
import it.progetto.energy.mapper.entitytodomain.UserEntityMapper;
import it.progetto.energy.model.UserDomain;
import it.progetto.energy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;
import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_TWO;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRuoliService implements UserService {

	private final UserAccessRepository userAccessRepository;
	private final RoleAccessRepository roleAccessRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserEntityMapper userEntityMapper;

	/**
	 * Recupera tutti gli User
	 * @deprecated
	 * @return List<User>
	 */
	@Deprecated
	@Override
	public List<UserDomain> findAllUser() {
		List<User> userList = userAccessRepository.findAll();
		return userEntityMapper.fromUserEntityListToUserDomainList(userList);
	}

	/**
	 * Recupera tutti gli User per pagina
	 * @return List<User>
	 */
	@Override
	public List<UserDomain> findAllUser(Pageable page) {
		List<User> userList = userAccessRepository.findAll(page)
				.getContent();
		return userEntityMapper.fromUserEntityListToUserDomainList(userList);
	}

	/**
	 * Metodo per inserire un User nel sistema
	 */
	@Override
	public UserDomain createUser(UserDomain userDomain) {
		if (Boolean.FALSE.equals(userAccessRepository.existsByUsername(userDomain.getUsername()))) {
			User user = userEntityMapper.fromUserDomainToUserEntity(userDomain);
			user.setPassword(passwordEncoder.encode(userDomain.getPassword()));
			Set<RoleAccess> roleAccessSet = userDomain.getRoleList().stream()
					.filter(Objects::nonNull)
					.map(role -> roleAccessRepository.findByRoleName(role.getRoleName()))
					.collect(Collectors.toSet());

			user.setRoles(roleAccessSet);
			User saved = userAccessRepository.save(user);
			return userEntityMapper.fromUserEntityToUserDomain(saved);
		} else {
			throw new ElementAlreadyPresentException(ERROR_ONE);
		}
	}

	/**
	 * Metodo per modificare un User nel sistema
	 */
	@Override
	public UserDomain updateUser(UserDomain userDomain) {
		if (Boolean.TRUE.equals(userAccessRepository.existsByUsername(userDomain.getUsername()))) {
			User userToUpdate = userAccessRepository.findById(userDomain.getId())
					.orElseThrow(() -> new NotUpdatableException(ERROR_TWO));

			//TODO MANAGE UPDATE
			User user = userEntityMapper.fromUserDomainToUserEntity(userDomain);
			user.setPassword(passwordEncoder.encode(userDomain.getPassword()));
			Set<RoleAccess> roleAccessSet = userDomain.getRoleList().stream()
					.filter(Objects::nonNull)
					.map(role -> roleAccessRepository.findByRoleName(role.getRoleName()))
					.collect(Collectors.toSet());
			user.setRoles(roleAccessSet);
			User updated = userAccessRepository.save(user);
			return userEntityMapper.fromUserEntityToUserDomain(updated);
		} else {
			throw new ElementAlreadyPresentException(ERROR_ONE);
		}
	}

	/**
	 * Metodo per eliminare un User
	 */
	@Override
	public void deleteUser(Long id) {
		if (userAccessRepository.existsById(id)) {
			userAccessRepository.deleteById(id);
			log.info("User id {} deleted", id);
		} else {
			throw new NotFoundException(ERROR_ONE);
		}
	}

}
