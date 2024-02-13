package it.progetto.energy.impl.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import it.progetto.energy.impl.configuration.UserDetailsImpl;
import it.progetto.energy.impl.dto.UserResponse;
import it.progetto.energy.impl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtils {

	@Value("${application.jwtSecret}")
	private String jwtSecret;

	@Value("${application.jwtExpirationMs}")
	private int jwtExpirationMs;

	private final UserService userService;

	@Autowired
    public JwtUtils(UserService userService) {
        this.userService = userService;
    }


    public String generateJwtToken(Authentication authentication) {
		log.info("generate jwt token" + authentication.getPrincipal());
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		log.info(userPrincipal.getUsername() + " - logged in");
		
		UserResponse userResponse = userService.getUserBasicInformations(userPrincipal.getUsername());
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", userResponse.getRole());
		claims.put("sub", userPrincipal.getUsername());
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser()
					.verifyWith(getKey())
					.build()
					.parseSignedClaims(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	private SecretKey getKey(){
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//		Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(jwt)
	}

}
