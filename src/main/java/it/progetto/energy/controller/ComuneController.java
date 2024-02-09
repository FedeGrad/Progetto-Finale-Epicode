package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.ComuneApi;
import it.progetto.energy.persistence.entity.Comune;
import it.progetto.energy.service.ComuneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comune")
@Tag(name = "Comune Controller", description = "Gestione Comuni")
@Slf4j
@RequiredArgsConstructor
public class ComuneController implements ComuneApi {

	private final ComuneService comuneServ;
	
	@Operation(summary = "Recupero Comuni",
			description = "Restituisce tutti i Comuni presenti nel sistema")
	@ApiResponse(responseCode = "200", description = "Comuni trovati")
	@ApiResponse(responseCode = "404", description = "Nessuna Comune trovato")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Comune> getAllComuni() {
		return comuneServ.getAllComuni();
	}
	
	@Operation(summary = "Recupero Comuni per pagina",
			   description = "Ritorna tutti i Comuni presenti nel sistema per pagina")
	@ApiResponse(responseCode = "200", description = "Comuni trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Comune trovato")
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<Comune> getAllComuni(Pageable page) {
		return comuneServ.getAllComuni(page);
	}

}
