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
import it.progetto.energy.repository.ProvinciaRepository;
import it.progetto.energy.service.ProvinciaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/provincia")
@Tag(name = "Provincia Controller", description = "Gestione delle Province")
public class ProvinciaController {

	@Autowired
	ProvinciaService provServ;

	@Deprecated
	@Operation(summary = "Recupero Province",
			description = "Restituisce tutte le Province presenti nel sistema")
	@ApiResponse(responseCode = "200", description = "Province trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Provincia trovata")
	@GetMapping
	public ResponseEntity getAllProvince() {
		return ResponseEntity.ok(provServ.getAllProvince());
	}
	
	@Operation(summary = "Recupero Province",
			description = "Restituisce tutte le Province presenti nel sistema per pagina")
	@ApiResponse(responseCode = "200", description = "Province trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Provincia trovata")
	@GetMapping("/gettAllProvincePaginate")
	public ResponseEntity getAllComuni(Pageable page) {
		return ResponseEntity.ok(provServ.getAllProvince(page));
	}

}
