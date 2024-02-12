package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.UserApi;
import it.progetto.energy.dto.user.UserDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.impl.model.User;
import it.progetto.energy.service.impl.UserRuoliService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "Gestione registrazione e accessi")
@Slf4j
@RequiredArgsConstructor
public class UserController implements UserApi {

	private final UserRuoliService userRuoliService;

	@Deprecated
	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<User> findAllUser() {
		return userRuoliService.getAllUser();
	}

	@Override
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<User> findAllUser(Pageable page) {
		return userRuoliService.getAllUser(page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void createUser(@Valid @RequestBody UserDTO userDTO) throws ElementAlreadyPresentException {
		userRuoliService.inserisciUser(userDTO);
		log.info("User created");
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public void updateUser(@RequestBody UserDTO updateDTO) {
		userRuoliService.modificaUser(updateDTO);
		log.info("User updated");
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") Long userId) {
		userRuoliService.eliminaUser(userId);
		log.info("User deleted");
	}

}
