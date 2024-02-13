package it.progetto.energy.impl.controller;

import it.progetto.energy.impl.configuration.UserDetailsImpl;
import it.progetto.energy.impl.dto.JwtResponse;
import it.progetto.energy.impl.model.LoginRequest;
import it.progetto.energy.impl.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins="*")
@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public JwtResponse login(@Valid @RequestBody LoginRequest request) {
		UsernamePasswordAuthenticationToken usrNameAuth = new UsernamePasswordAuthenticationToken( 
				request.getUserName(), 
				request.getPassword()
		);
		Authentication authentication = authManager.authenticate(usrNameAuth);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities()
								.stream()
								.map(GrantedAuthority::getAuthority)
								.toList();

		return new JwtResponse(
				jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				roles
			);
		
	}
	
	@PostMapping("/login/jwt")
	public ResponseEntity<String> loginJwt(@Valid @RequestBody LoginRequest request) {
		log.info("login - jwt "+request);
		UsernamePasswordAuthenticationToken usrNameAuth = new UsernamePasswordAuthenticationToken( 
				request.getUserName(), 
				request.getPassword()
		);
		Authentication authentication = authManager.authenticate(usrNameAuth);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken = jwtUtils.generateJwtToken(authentication);
		return ResponseEntity.ok(jwtToken);
	}
	
}
