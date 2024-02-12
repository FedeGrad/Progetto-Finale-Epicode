package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.UserApi;
import it.progetto.energy.dto.user.UserDTO;
import it.progetto.energy.dto.user.UserOutputDTO;
import it.progetto.energy.dto.user.UserUpdateDTO;
import it.progetto.energy.mapper.dtotodomain.UserDTOMapper;
import it.progetto.energy.model.UserDomain;
import it.progetto.energy.service.impl.UserRuoliService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	private final UserDTOMapper userDTOMapper;

	@Deprecated
	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<UserOutputDTO> findAllUser() {
		List<UserDomain> userDomainList = userRuoliService.findAllUser();
		return userDTOMapper.fromUserDomainListToUserOutputDTOList(userDomainList);
	}

	@Override
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public List<UserOutputDTO> findAllUser(Pageable page) {
		List<UserDomain> userDomainList = userRuoliService.findAllUser(page);
		return userDTOMapper.fromUserDomainListToUserOutputDTOList(userDomainList);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public UserOutputDTO createUser(@Valid @RequestBody UserDTO userDTO) {
		UserDomain userDomain = userDTOMapper.fromUserDTOToUserDomain(userDTO);
		UserDomain userCreated = userRuoliService.createUser(userDomain);
		return userDTOMapper.fromUserDomainToUserOutputDTO(userCreated);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public UserOutputDTO updateUser(@RequestBody UserUpdateDTO updateDTO) {
		UserDomain userDomain = userDTOMapper.fromUserUpdateDTOToUserDomain(updateDTO);
		UserDomain userUpdated = userRuoliService.updateUser(userDomain);
		return userDTOMapper.fromUserDomainToUserOutputDTO(userUpdated);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") Long userId) {
		userRuoliService.deleteUser(userId);
		log.info("User deleted");
	}

}
