package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.InvoiceApi;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.RangeDTO;
import it.progetto.energy.dto.StatoDTO;
import it.progetto.energy.dto.invoice.InvoiceAddPDFDTO;
import it.progetto.energy.dto.invoice.InvoiceDTO;
import it.progetto.energy.dto.invoice.InvoiceUpdateDTO;
import it.progetto.energy.model.StatoFattura;
import it.progetto.energy.persistence.entity.Fattura;
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
	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Fattura> findAllInvoice() {
		return fatturaService.getAllFatture();
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public Page<Fattura> findAllInvoice(Pageable page) {
		return fatturaService.getAllFatture(page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/customer/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Fattura> findInvoiceByCustomer(@PathVariable("id") Long invoiceId) {
		return fatturaService.getFatturaByCliente(invoiceId);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/state")
	@ResponseStatus(HttpStatus.OK)
	public Page<Fattura> findInvoiceByState(@RequestBody StatoDTO statoDTO, Pageable page) {
		StatoFattura statoFattura = statoDTO.getStato();
		return fatturaService.getFatturaByStato(statoFattura, page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/date")
	@ResponseStatus(HttpStatus.OK)
	public Page<Fattura> findInvoiceByDate(@RequestBody DataDTO dataDTO, Pageable page) {
		return fatturaService.getFatturaByData(dataDTO, page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/year/{year}")
	@ResponseStatus(HttpStatus.OK)
	public Page<Fattura> findInvoiceByYear(@PathVariable("year") Integer year, Pageable page) {
		return fatturaService.getFatturaByAnno(year, page);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/range")
	@ResponseStatus(HttpStatus.OK)
	public Page<Fattura> findInvoiceByRange(@RequestBody RangeDTO rangeDTO, Pageable page) {
		return fatturaService.getFatturaByImporto(rangeDTO, page);
	}

	//TODO DA IMPLEMENTARE
	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(consumes = {MediaType.MULTIPART_MIXED_VALUE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void createInvoice(@Valid @ModelAttribute InvoiceDTO invoiceDTO) throws IOException {
		fatturaService.inserisciFattura(invoiceDTO);
		log.info("Invoice created");
	}

	//TODO IMPLEMENTARE
	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void uploadInvoice(@RequestHeader String token,
							  @ModelAttribute InvoiceAddPDFDTO invoiceAddPDFDTO) throws IOException {
		log.info("{}, {}, {}", token, invoiceAddPDFDTO.getIdFattura(), invoiceAddPDFDTO.getFileFattura().getOriginalFilename());
//				fatturaServ.inserisciFattuaPDF(fatturaPDFDTO);
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateInvoice(@Valid @RequestBody InvoiceUpdateDTO invoiceUpdateDTO) {
		fatturaService.modificaFattura(invoiceUpdateDTO);
		log.info("Invoice updated");
	}

	@Override
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteInvoice(@PathVariable("id") Long invoiceId) {
		fatturaService.eliminaFattura(invoiceId);
		log.info("Invoice deleted");
	}

}
