package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.InvoiceApi;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.FatturaDTO;
import it.progetto.energy.dto.FatturaModificaDTO;
import it.progetto.energy.dto.FatturaPDFDTO;
import it.progetto.energy.dto.RangeDTO;
import it.progetto.energy.dto.StatoDTO;
import it.progetto.energy.persistence.entity.Fattura;
import it.progetto.energy.persistence.entity.StatoFattura;
import it.progetto.energy.service.FatturaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/invoice")
@Tag(name = "Fattura Controller", description = "Gestione delle fatture")
@Slf4j
@RequiredArgsConstructor
public class InvoiceController implements InvoiceApi {

	private final FatturaService fatturaService;

	@Deprecated
	@Operation(summary = "Recupera Fatture",
			description = "Restituisce tutte le Fatture presenti nel sistema")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Fattura> findAllInvoice() {
		return fatturaService.getAllFatture();
	}

	@Operation(summary = "Recupera Fatture per pagina",
			description = "Restituisce tutte le Fatture presenti nel sistema per pagina")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<Fattura> findAllInvoice(Pageable page) {
		return fatturaService.getAllFatture(page);
	}

	@Operation(summary = "Recupera fatture per ID",
			description = "Restituisce tutte le Fatture di un Cliente tramite l'ID Cliente")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/customer/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Fattura> findInvoiceByCustomer(@PathVariable("id") Long id) {
		return fatturaService.getFatturaByCliente(id);
	}

	@Operation(summary = "Recupera Fatture per stato",
			description = "Restituisce tutte le Fatture in un determinato stato")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/state")
	@ResponseStatus(HttpStatus.OK)
	public Page<Fattura> findInvoiceByState(@RequestBody StatoDTO dto, Pageable page) {
		StatoFattura statoFattura = dto.getStato();
		return fatturaService.getFatturaByStato(statoFattura, page);
	}

	@Operation(summary = "Recupera Fatture per data",
			description = "Restituisce tutte le Fatture per data")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/date")
	@ResponseStatus(HttpStatus.OK)
	public Page<Fattura> findInvoiceByDate(@RequestBody DataDTO data, Pageable page) {
		return fatturaService.getFatturaByData(data, page);
	}

	@Operation(summary = "Recupera Fatture per anno",
			description = "Restituisce tutte le Fatture di un determinato anno")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/year/{year}")
	@ResponseStatus(HttpStatus.OK)
	public Page<Fattura> findInvoiceByYear(@PathVariable("year") Integer anno, Pageable page) {
		return fatturaService.getFatturaByAnno(anno, page);
	}

	@Operation(summary = "Recupera Fatture by range",
			description = "Restituisce tutte le Fatture in un range")
	@ApiResponse(responseCode = "200", description = "Fatture trovate")
	@ApiResponse(responseCode = "404", description = "Nessuna Fattura trovata")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/range")
	@ResponseStatus(HttpStatus.OK)
	public Page<Fattura> findInvoiceByRange(@RequestBody RangeDTO rangeDTO, Pageable page) {
		return fatturaService.getFatturaByImporto(rangeDTO, page);
	}

	//TODO DA IMPLEMENTARE
	@Operation(summary = "Crea Fattura in PDF",
			description = "Inserisci i dati della fattura che verrà creata e aggiunta al DB")
	@ApiResponse(responseCode = "204", description = "Fattura creata/inserita correttamente")
	@ApiResponse(responseCode = "500", description = "ERRORE creazione/inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(consumes = {MediaType.MULTIPART_MIXED_VALUE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void createInvoice(@Valid @ModelAttribute FatturaDTO fatturaDTO) throws IOException {
		fatturaService.inserisciFattura(fatturaDTO);
		log.info("Invoice created");
	}

	//TODO IMPLEMENTARE
	@Operation(summary = "Aggiunge Fattura File",
			description = "Inserisci l'id della fattura pre-creata e il File")
	@ApiResponse(responseCode = "200", description = "Fattura creata/inserita correttamente")
	@ApiResponse(responseCode = "500", description = "ERRORE creazione/inserimento")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void uploadInvoice(@RequestHeader String token,
											  @ModelAttribute FatturaPDFDTO fatturaPDFDTO) throws IOException {
		log.info("{}, {}, {}", token + fatturaPDFDTO.getIdFattura() + fatturaPDFDTO.getFileFattura().getOriginalFilename());
//				fatturaServ.inserisciFattuaPDF(fatturaPDFDTO);
	}

	@Operation(summary = "Modifica Fattura",
			description = "Modifica una Fattura presente nel sistema")
	@ApiResponse(responseCode = "204", description = "Fattura modificata")
	@ApiResponse(responseCode = "404", description = "Fattura non trovata")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateInvoice(@Valid @RequestBody FatturaModificaDTO modificaDTO) {
		fatturaService.modificaFattura(modificaDTO);
		log.info("Invoice updated");
	}

	@Operation(summary = "Elimina Fattura",
			description = "Elimina una Fattura presente nel sistema")
	@ApiResponse(responseCode = "204", description = "Fattura eliminata")
	@ApiResponse(responseCode = "404", description = "Fattura non trovata")
	@ApiResponse(responseCode = "500", description = "Errore modifica")
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteInvoice(@PathVariable("id") Long id) {
		fatturaService.eliminaFattura(id);
		log.info("Invoice deleted");
	}

}
