package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.progetto.energy.repository.ComuneRepository;
import it.progetto.energy.service.ComuneService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/comune")
@Tag(name = "Comune Controller", description = "Gestione Comuni")
public class ComuneController {

	@Autowired
	ComuneService comuneServ;
	
	@Operation(summary = "Recupero Comuni",
			description = "Restituisce tutti i Comuni presenti nel sistema")
	@ApiResponse(responseCode = "200", description = "Comuni trovati")
	@ApiResponse(responseCode = "404", description = "Nessuna Comune trovato")
	@GetMapping
	public ResponseEntity getAllComuni() {
		return ResponseEntity.ok(comuneServ.getAllComuni());
	}
	
	@Operation(summary = "Recupero Comuni per pagina",
			   description = "Ritorna tutti i Comuni presenti nel sistema per pagina")
	@ApiResponse(responseCode = "200", description = "Comuni trovati")
	@ApiResponse(responseCode = "404", description = "Nessun Comune trovato")
	@GetMapping("/gettAllComuni")
	public ResponseEntity getAllComuni(Pageable page) {
		return ResponseEntity.ok(comuneServ.getAllComuni(page));
	}

}
