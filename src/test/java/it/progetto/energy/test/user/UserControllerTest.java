package it.progetto.energy.test.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import it.progetto.energy.BasicTests;
import it.progetto.energy.impl.LoginRequest;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class UserControllerTest extends BasicTests {

	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;

	@Override
	protected String getEntryPoint() {
		return "http://localhost:" + port + "/api/auth/loging/jwt";
	}

	@Test
	protected void getAdminTokenTest() {
		String url = "http://localhost:" + port + "/api/auth/login/jwt";
		LoginRequest login = new LoginRequest();
		login.setUserName("federico");
		login.setPassword("fox");
		log.info("---------------------" + login);
		HttpEntity<LoginRequest> loginRequest = new HttpEntity<LoginRequest>(login);
		String jwt = restTemplate.postForObject(url, loginRequest, String.class);
		log.info("---------------------" + jwt);
	}

	@Test
	protected void getUserTokenTest() {
		String url = "http://localhost:" + port + "/api/auth/login/jwt";
		LoginRequest login = new LoginRequest();
		login.setUserName("ilCane");
		login.setPassword("fox");
		log.info("---------------------" + login);
		HttpEntity<LoginRequest> loginRequest = new HttpEntity<LoginRequest>(login);
		String jwt = restTemplate.postForObject(url, loginRequest, String.class);
		log.info("---------------------" + jwt);
	}

}
