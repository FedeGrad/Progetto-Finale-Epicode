
package it.progetto.energy.impl.service;

import java.util.List;
import java.util.stream.Collectors;

import it.progetto.energy.impl.dto.UserResponse;
import it.progetto.energy.impl.model.User;
import it.progetto.energy.impl.repository.UserAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired
	UserAccessRepository userRepository;
	
	public List<UserResponse> getAllUsersBasicInformations() {
		return userRepository.findAll()
				.stream()
				.map( user ->  UserResponse
								.builder()
								.userName(  user.getUsername()  )
								.role( user.getRoles().stream().findFirst().get().getRoleName().name() )
								.build()   
				).collect(Collectors.toList());
	}
	
	public UserResponse getUserBasicInformations(String userName) {
		User user = userRepository.findByUsername(userName).get();

		return UserResponse
		.builder()
		.userName(userName)
		.role( user.getRoles().stream().findFirst().get().getRoleName().name()).build();
	}
	

}
