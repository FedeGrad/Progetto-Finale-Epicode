package it.progetto.energy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.progetto.energy.controller.api.InvoiceApi;
import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.PageDTO;
import it.progetto.energy.dto.RangeDTO;
import it.progetto.energy.dto.StatoDTO;
import it.progetto.energy.dto.invoice.InvoiceDTO;
import it.progetto.energy.dto.invoice.InvoiceOutputDTO;
import it.progetto.energy.dto.invoice.InvoiceUpdateDTO;
import it.progetto.energy.dto.invoice.InvoiceUploadPdfDTO;
import it.progetto.energy.mapper.UtilsMapper;
import it.progetto.energy.mapper.dtotodomain.InvoiceDTOMapper;
import it.progetto.energy.model.InvoiceDomain;
import it.progetto.energy.model.PageDomain;
import it.progetto.energy.model.StatoFattura;
import it.progetto.energy.service.impl.InvoiceServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

	private final InvoiceServiceImpl invoiceService;
	private final InvoiceDTOMapper invoiceDTOMapper;
	private final UtilsMapper utilsMapper;

	@Deprecated
	@Override
//	@SecurityRequirement(name = "bearerAuth")
//	@PreAuthorize("isAuthenticated()")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InvoiceOutputDTO> findAllInvoice() {
		List<InvoiceDomain> invoiceList = invoiceService.findAllInvoice();
		return invoiceDTOMapper.fromInvoiceListDomainToInvoiceOutputDTOList(invoiceList);
	}

	@Override
//	@SecurityRequirement(name = "bearerAuth")
//	@PreAuthorize("isAuthenticated()")
	@GetMapping("/page")
	@ResponseStatus(HttpStatus.OK)
	public List<InvoiceOutputDTO> findAllInvoicePaged(PageDTO pageDTO) {
		PageDomain pageDomain = utilsMapper.fromPageDTOToPageDomain(pageDTO);
		List<InvoiceDomain> invoicePage = invoiceService.findAllInvoice(pageDomain);
		return invoiceDTOMapper.fromInvoiceListDomainToInvoiceOutputDTOList(invoicePage);
	}

	@Override
//	@SecurityRequirement(name = "bearerAuth")
//	@PreAuthorize("isAuthenticated()")
	@GetMapping("/customer/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<InvoiceOutputDTO> findInvoiceByCustomer(@PathVariable("id") Long invoiceId) {
		List<InvoiceDomain> invoiceList = invoiceService.findInvoiceByCustomer(invoiceId);
		return invoiceDTOMapper.fromInvoiceListDomainToInvoiceOutputDTOList(invoiceList);
	}

	@Override
//	@SecurityRequirement(name = "bearerAuth")
//	@PreAuthorize("isAuthenticated()")
	@PostMapping("/state")
	@ResponseStatus(HttpStatus.OK)
	public List<InvoiceOutputDTO> findInvoiceByState(@RequestBody StatoDTO statoDTO, @RequestBody PageDTO pageDTO) {
		StatoFattura statoFattura = statoDTO.getStato();
		PageDomain pageDomain = utilsMapper.fromPageDTOToPageDomain(pageDTO);
		List<InvoiceDomain> invoicePage = invoiceService.findInvoiceByState(statoFattura, pageDomain);
		return invoiceDTOMapper.fromInvoiceListDomainToInvoiceOutputDTOList(invoicePage);
	}

	@Override
//	@SecurityRequirement(name = "bearerAuth")
//	@PreAuthorize("isAuthenticated()")
	@PostMapping("/date")
	@ResponseStatus(HttpStatus.OK)
	public List<InvoiceOutputDTO> findInvoiceByDate(@RequestBody DataDTO dataDTO, @RequestBody PageDTO pageDTO) {
		PageDomain pageDomain = utilsMapper.fromPageDTOToPageDomain(pageDTO);
		List<InvoiceDomain> invoicePage = invoiceService.findInvoiceByDate(dataDTO, pageDomain);
		return invoiceDTOMapper.fromInvoiceListDomainToInvoiceOutputDTOList(invoicePage);
	}

	@Override
//	@SecurityRequirement(name = "bearerAuth")
//	@PreAuthorize("isAuthenticated()")
	@GetMapping("/year/{year}")
	@ResponseStatus(HttpStatus.OK)
	public List<InvoiceOutputDTO> findInvoiceByYear(@PathVariable("year") String year, @RequestBody PageDTO pageDTO) {
		PageDomain pageDomain = utilsMapper.fromPageDTOToPageDomain(pageDTO);
		List<InvoiceDomain> invoicePage = invoiceService.findInvoiceByYear(year, pageDomain);
		return invoiceDTOMapper.fromInvoiceListDomainToInvoiceOutputDTOList(invoicePage);
	}

	@Override
//	@SecurityRequirement(name = "bearerAuth")
//	@PreAuthorize("isAuthenticated()")
	@PostMapping("/range")
	@ResponseStatus(HttpStatus.OK)
	public List<InvoiceOutputDTO> findInvoiceByRange(@RequestBody RangeDTO rangeDTO, @RequestBody PageDTO pageDTO) {
		PageDomain pageDomain = utilsMapper.fromPageDTOToPageDomain(pageDTO);
		List<InvoiceDomain> invoicePage = invoiceService.findInvoiceByAmountBetween(rangeDTO, pageDomain);
		return invoiceDTOMapper.fromInvoiceListDomainToInvoiceOutputDTOList(invoicePage);
	}

	@Override
//	@SecurityRequirement(name = "bearerAuth")
//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(consumes = {MediaType.MULTIPART_MIXED_VALUE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public InvoiceOutputDTO createInvoice(@Valid @ModelAttribute InvoiceDTO invoiceDTO) {
		InvoiceDomain invoiceDomain = invoiceDTOMapper.fromInvoiceDTOToInvoiceDomain(invoiceDTO);
		InvoiceDomain invoiceCreated = invoiceService.createInvoice(invoiceDomain);
		return invoiceDTOMapper.fromInvoiceDomainToInvoiceOutputDTO(invoiceCreated);
	}

	@Override
//	@SecurityRequirement(name = "bearerAuth")
//	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public InvoiceOutputDTO updateInvoice(@Valid @RequestBody InvoiceUpdateDTO invoiceUpdateDTO) {
		InvoiceDomain invoiceDomain = invoiceDTOMapper.fromInvoiceUpdateDTOToInvoiceDomain(invoiceUpdateDTO);
		InvoiceDomain invoiceUpdated = invoiceService.updateInvoice(invoiceDomain);
		return invoiceDTOMapper.fromInvoiceDomainToInvoiceOutputDTO(invoiceUpdated);
	}

	//TODO IMPLEMENTARE
	@Override
//	@SecurityRequirement(name = "bearerAuth")
//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void uploadInvoice(@RequestHeader String token,
							  @ModelAttribute InvoiceUploadPdfDTO invoiceUploadPdfDTO) throws IOException {
		log.info("{}, {}, {}", token, invoiceUploadPdfDTO.getInvoiceId(), invoiceUploadPdfDTO.getInvoicePDF().getOriginalFilename());
		invoiceService.uploadInvoicePDF(invoiceUploadPdfDTO);
	}

	@Override
//	@SecurityRequirement(name = "bearerAuth")
//	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteInvoice(@PathVariable("id") Long invoiceId) {
		invoiceService.deleteInvoice(invoiceId);
	}

}
