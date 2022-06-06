package it.progetto.energy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import it.progetto.energy.impl.LoginRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BasicTests {

	  @LocalServerPort
	    protected int port;

	    @Autowired
	    protected TestRestTemplate restTemplate;

	    protected abstract String getEntryPoint();
	    
		protected String getAdminToken() {
			String url = "http://localhost:" + port + "/api/auth/login/jwt";
			LoginRequest login = new LoginRequest();
			login.setUserName("federico");
			login.setPassword("fox");
			log.info("---------------------"+login);
			HttpEntity<LoginRequest> loginRequest = new HttpEntity<LoginRequest>(login);
			String jwt = restTemplate.postForObject(url, loginRequest, String.class);
			log.info("---------------------"+jwt);
			return jwt;
		}
		
		protected String getUserToken() {
			String url = "http://localhost:" + port + "/api/auth/login/jwt";
			LoginRequest login = new LoginRequest();
			login.setUserName("ilCane");
			login.setPassword("fox");
			log.info("---------------------"+login);
			HttpEntity<LoginRequest> loginRequest = new HttpEntity<LoginRequest>(login);
			String jwt = restTemplate.postForObject(url, loginRequest, String.class);
			log.info("---------------------"+jwt);
			return jwt;
		}
		
		protected HttpHeaders getAdminHeader() {
			HttpHeaders header = new HttpHeaders();
			String jwt = getAdminToken();
			header.set("Authorization", "Bearer " + jwt);
			return header;
		}

		protected HttpHeaders getUserHeader() {
			HttpHeaders header = new HttpHeaders();
			String jwt = getUserToken();
			header.set("Authorization", "Bearer " + jwt);
			return header;
		}

	    protected HttpEntity getUnauthorizedEntity() {
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "");
	        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
	        return jwtEntity;
	    }

	    protected HttpEntity getAuthorizedEntity() {
	        String jwt = getAdminToken();
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "Bearer " + jwt);
	        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
	        return jwtEntity;
	    }

	
	
}
