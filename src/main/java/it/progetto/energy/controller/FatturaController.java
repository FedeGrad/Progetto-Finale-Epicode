package it.progetto.energy.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.dto.*;
import it.progetto.energy.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.progetto.energy.exception.WrongInsertException;
import it.progetto.energy.model.StatoFattura;
import it.progetto.energy.repository.FatturaRepository;
import it.progetto.energy.service.FatturaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/fattura")
@Slf4j
@Tag(name = "Fattura Controller", description = "Gestione delle fatture")
public class FatturaController {

	@Autowired
	FatturaService fatturaServ;

	@Deprecated
	@Operation(summary = "Recupera Fatture",
			description = "Restituisce tutte le Fatture presenti nel sistema")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity getAllFatture() {
		return ResponseEntity.ok(fatturaServ.getAllFatture());
	}

	@Operation(summary = "Recupera Fatture per pagina",
			description = "Restituisce tutte le Fatture presenti nel sistema per pagina")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getFatturePaginate")
	public ResponseEntity getAllFatture(Pageable page) {
		return ResponseEntity.ok(fatturaServ.getAllFatture(page));
	}

	@Operation(summary = "Recupera fatture per ID",
			description = "Restituisce tutte le Fatture di un Cliente tramite l'ID Cliente")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getFattureByCliente/{id}")
	public ResponseEntity getFattureByCliente(@PathParam("id") Long id) {
		return ResponseEntity.ok(fatturaServ.getFatturaByCliente(id));
	}

	@Operation(summary = "Recupera Fatture per stato",
			description = "Restituisce tutte le Fatture in un determinato stato")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/getFattureByStato/{stato}")
	public ResponseEntity getFattureByStato(@RequestBody StatoDTO dto, Pageable page) throws WrongInsertException {
		StatoFattura statoF = dto.getStato();
		return ResponseEntity.ok(fatturaServ.getFatturaByStato(statoF, page));
	}

	@Operation(summary = "Recupera Fatture per data",
			description = "Restituisce tutte le Fatture per data")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/getFattureByData")
	public ResponseEntity getFattureByData(@RequestBody DataDTO data, Pageable page) {
		// DateTimeFormatter dtForm = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return ResponseEntity.ok(fatturaServ.getFatturaByData(data, page));
	}

	@Operation(summary = "Recupera Fatture per anno",
			description = "Restituisce tutte le Fatture di un determinato anno")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getFattureByAnno/{anno}")
	public ResponseEntity getFattureByAnno(@PathParam("anno") Integer anno, Pageable page) {
		return ResponseEntity.ok(fatturaServ.getFatturaByAnno(anno, page));
	}

	@Operation(summary = "Recupera Fatture by range",
			description = "Restituisce tutte le Fatture in un range")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/getFattureByRange")
	public ResponseEntity getFattureByRange(@RequestBody RangeDTO dto, Pageable page) {
		return ResponseEntity.ok(fatturaServ.getFatturaByImporto(dto, page));
	}

	//TODO DA IMPLEMENTARE
	@Operation(summary = "Crea Fattura in PDF",
			description = "Inserisci i dati della fattura che verrà creata e aggiunta al DB")
	@ApiResponse(responseCode = "200", description = "Fattura creata/inserita correttamente")
	@ApiResponse(responseCode = "500", description = "ERRORE creazione/inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/creaFattura",
			consumes = {MediaType.MULTIPART_MIXED_VALUE})
	public ResponseEntity<?> creaFattura(@Valid @ModelAttribute FatturaDTO dto) throws IOException {
		fatturaServ.inserisciFattura(dto);
		return ResponseEntity.ok("Fattura inserita");
	}

	//TODO IMPLEMENTARE
	@Operation(summary = "Aggiunge Fattura File",
			description = "Inserisci l'id della fattura pre-creata e il File")
	@ApiResponse(responseCode = "200", description = "Fattura creata/inserita correttamente")
	@ApiResponse(responseCode = "500", description = "ERRORE creazione/inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/uploadFattura", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> inserisciFattura(@RequestHeader String token,
											  @ModelAttribute FatturaPDFDTO fatturaPDFDTO) throws IOException {
		log.info(token + " " + fatturaPDFDTO.getIdFattura() + " "
		+ fatturaPDFDTO.getFileFattura().getOriginalFilename());
		return ResponseEntity.ok().build();
//				fatturaServ.inserisciFattuaPDF(fatturaPDFDTO);
	}

	@Operation(summary = "Modifica Fattura",
			description = "Modifica una Fattura presente nel sistema")
	@ApiResponse(responseCode = "200", description = "Fattura modificata")
	@ApiResponse(responseCode = "404", description = "Fattura non trovata")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity modificaFattura(@Valid @RequestBody FatturaModificaDTO modificaDTO) {
		fatturaServ.modificaFattura(modificaDTO);
		return ResponseEntity.ok("Fattura modificata");
	}

	@Operation(summary = "Elimina Fattura",
			description = "Elimina una Fattura presente nel sistema")
	@ApiResponse(responseCode = "200", description = "Fattura eliminata")
	@ApiResponse(responseCode = "404", description = "Fattura non trovata")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity eliminaFattura(@PathVariable Long id) {
		fatturaServ.eliminaFattura(id);
		return ResponseEntity.ok("Fattura eliminata");
	}

}
