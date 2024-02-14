
package it.progetto.energy.impl.service;

import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.impl.dto.UserResponse;
import it.progetto.energy.impl.model.User;
import it.progetto.energy.impl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static it.progetto.energy.exception.model.ErrorCodeDomain.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public List<UserResponse> getAllUsersBasicInformation() {
		return userRepository.findAll()
				.stream()
				.map(user -> UserResponse
						.builder()
						.userName(user.getUsername())
						.role(Objects.requireNonNull(user.getRoles().stream()
										.findFirst()
										.orElse(null))
								.getRoleName()
								.name())
						.build()
				).toList();
	}

	public UserResponse getUserBasicInformation(String userName) {
		User user = userRepository.findByUsername(userName)
				.orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

		return UserResponse
				.builder()
				.userName(userName)
				.role(Objects.requireNonNull(user.getRoles().stream()
								.findFirst()
								.orElse(null))
						.getRoleName()
						.name())
				.build();
	}

}
