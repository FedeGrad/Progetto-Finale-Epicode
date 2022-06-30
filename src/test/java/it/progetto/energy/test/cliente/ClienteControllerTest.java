package it.progetto.energy.test.cliente;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.progetto.energy.BasicTests;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class ClienteControllerTest extends BasicTests {

	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;

	@Override
	protected String getEntryPoint() {
		return "http://localhost:" + port + "/cliente";
	}
	
	@Test
	@Order(1)
	void getAllClienti() {
		String url = getEntryPoint() + "/";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		HttpEntity<String> admin = new HttpEntity<String>(getAdminHeader());
		response = restTemplate.exchange(url, HttpMethod.GET, admin, String.class);
		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		HttpEntity<String> user = new HttpEntity<String>(getUserHeader());
		response = restTemplate.exchange(url, HttpMethod.GET, user, String.class);
		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
	}
	

}
