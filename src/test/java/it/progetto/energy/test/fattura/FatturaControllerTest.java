package it.progetto.energy.test.fattura;

import it.progetto.energy.BasicTests;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class FatturaControllerTest extends BasicTests {

	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;

	@Override
	protected String getEntryPoint() {
		return "http://localhost:" + port + "/fattura";
	}

	/**
	@Test
	@Order(1)
	void getAllFatture() {
		log.info("----------get all fatture");
		String url = "http://localhost:" + port + "/fattura";
		ResponseEntity<String> r = restTemplate.getForEntity(url, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);

		HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
		r = restTemplate.exchange(url, HttpMethod.GET, adminEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

		HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
		r = restTemplate.exchange(url, HttpMethod.GET, userEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	}
	*/
	// @Test
	// @Order(2)
	// void inserisciFattura() {
	// log.info("----------inserisci fattura");
	// String url = "http://localhost:" + port + "/fattura";
	// FatturaDTO fatturaMod = new FatturaDTO();
	// fatturaMod.setAnno(2022);
	// fatturaMod.setData(LocalDate.now());
	// fatturaMod.setIdCliente(1l);
	// fatturaMod.setImporto(520000d);
	// fatturaMod.setNumero(1);
	// fatturaMod.setStato("PAGATA");
	// // test senza autenticazione (ne user ne admin)
	// HttpEntity<FatturaDTO> fatturaEntity = new
	// HttpEntity<FatturaDTO>(fatturaMod);
	// ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.POST,
	// fatturaEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
	//
	// // test con admin (autorizzato)
	// HttpEntity<FatturaDTO> adminEntity = new HttpEntity<FatturaDTO>(fatturaMod,
	// getAdminHeader());
	// r = restTemplate.exchange(url, HttpMethod.POST, adminEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	//
	// // test con user (non autorizzato)
	// HttpEntity<FatturaDTO> userEntity = new HttpEntity<FatturaDTO>(fatturaMod,
	// getUserHeader());
	// r = restTemplate.exchange(url, HttpMethod.POST, userEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
	// }

	// @Test
	// @Order(3)
	// void modificaFattura() {
	// log.info("----------modifica fattura");
	// String url = "http://localhost:" + port + "/fattura";
	// FatturaModificaDTO fatturaMod = new FatturaModificaDTO();
	// fatturaMod.setIdFattura(2l);
	// fatturaMod.setAnno(2022);
	// fatturaMod.setData(LocalDate.now());
	// fatturaMod.setIdCliente(1l);
	// fatturaMod.setImporto(520000d);
	// fatturaMod.setNumero(1);
	// fatturaMod.setStato("PAGATA");
	// // test senza autenticazione (ne user ne admin)
	// HttpEntity<FatturaModificaDTO> fatturaEntity = new
	// HttpEntity<FatturaModificaDTO>(fatturaMod);
	// ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.PUT,
	// fatturaEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
	//
	// // test con admin (autorizzato)
	// HttpEntity<FatturaModificaDTO> adminEntity = new
	// HttpEntity<FatturaModificaDTO>(fatturaMod, getAdminHeader());
	// r = restTemplate.exchange(url, HttpMethod.PUT, adminEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	//
	// // test con user (non autorizzato)
	// HttpEntity<FatturaModificaDTO> userEntity = new
	// HttpEntity<FatturaModificaDTO>(fatturaMod, getUserHeader());
	// r = restTemplate.exchange(url, HttpMethod.PUT, userEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
	// }

	// @Test
	// void getFattureByData() {
	// log.info("----------get fatture by stato");
	// String url = "http://localhost:" + port +
	// "/fattura/getFattureByData/2022-05-27";
	// ResponseEntity<String> r = restTemplate.getForEntity(url, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
	//
	// HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
	// r = restTemplate.exchange(url, HttpMethod.POST, adminEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	//
	// HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
	// r = restTemplate.exchange(url, HttpMethod.POST, userEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	// }

	// @Test
	// void getFattureByStato() {
	// log.info("----------get fatture by stato");
	// String url = "http://localhost:" + port + "/fattura/getFattureByStato";
	// // test senza autenticazione (ne user ne admin)
	// StatoDTO dto = new StatoDTO();
	// dto.setStato(StatoFattura.PAGATA);
	// HttpEntity<StatoDTO> fatturaEntity = new HttpEntity<StatoDTO>(dto);
	// ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.POST,
	// fatturaEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
	//
	// // test con admin (autorizzato)
	// HttpEntity<StatoDTO> adminEntity = new HttpEntity<StatoDTO>(dto,
	// getAdminHeader());
	// r = restTemplate.exchange(url, HttpMethod.POST, adminEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	//
	// // test con user (non autorizzato)
	// HttpEntity<StatoDTO> userEntity = new HttpEntity<StatoDTO>(dto,
	// getUserHeader());
	// r = restTemplate.exchange(url, HttpMethod.POST, userEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	// }

	// @Test
	// void getFattureByCliente() {
	// log.info("----------get by cliente");
	// String url = "http://localhost:" + port + "/fattura/getFattureByCliente/1";
	// ResponseEntity<String> r = restTemplate.getForEntity(url, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
	//
	// HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
	// r = restTemplate.exchange(url, HttpMethod.GET, adminEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	//
	// HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
	// r = restTemplate.exchange(url, HttpMethod.GET, adminEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	// }

	// @Test
	// @Order(4)
	// void eliminaFattura() {
	// String url = "http://localhost:" + port + "/fattura/2";
	// // test senza autenticazione
	// ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.DELETE,
	// HttpEntity.EMPTY, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
	//
	// // test con autenticazione admin
	// HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
	// r = restTemplate.exchange(url, HttpMethod.DELETE, adminEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	//
	// // test con autenticazione user
	// HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
	// r = restTemplate.exchange(url, HttpMethod.DELETE, userEntity, String.class);
	// assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
	// }

}
