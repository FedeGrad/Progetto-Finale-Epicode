package it.progetto.energy.service;

import it.progetto.energy.dto.DataDTO;
import it.progetto.energy.dto.RangeDTO;
import it.progetto.energy.dto.invoice.InvoiceAddPDFDTO;
import it.progetto.energy.exception.NotCreatableException;
import it.progetto.energy.exception.NotFoundException;
import it.progetto.energy.exception.NotUpdatableException;
import it.progetto.energy.mapper.entitytodomain.InvoiceEntityMapper;
import it.progetto.energy.model.InvoiceDomain;
import it.progetto.energy.model.StatoFattura;
import it.progetto.energy.persistence.entity.Cliente;
import it.progetto.energy.persistence.entity.Fattura;
import it.progetto.energy.persistence.repository.ClienteRepository;
import it.progetto.energy.persistence.repository.FatturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_ONE;
import static it.progetto.energy.exception.model.ErrorCodeDomain.ERROR_TWO;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvoiceService extends FileService{

	private final FatturaRepository fatturaRepository;
	private final ClienteRepository clienteRepository;
	private final CustomerService customerService;
	private final InvoiceEntityMapper invoiceEntityMapper;

	/**
	 * Recupera tutte le fatture
	 * @deprecated
	 */
	public List<InvoiceDomain> getAllFatture() {
		List<Fattura> invoiceList = fatturaRepository.findAll();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoiceList);
	}

	/**
	 * Recupera tutte le fatture, paginate
	 */
	public List<InvoiceDomain> getAllFatture(Pageable page) {
		List<Fattura> invoicePage = fatturaRepository.findAll(page)
				.getContent();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoicePage);
	}

	/**
	 * Recupera tutte le fatture di un CLiente
	 */
	public List<InvoiceDomain> getFatturaByCliente(Long idCliente) {
		List<Fattura> invoiceList = fatturaRepository.findByCliente(idCliente);
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoiceList);
	}

	/**
	 * Recupera tutte le fatture di un determinato importo
	 */
	public List<InvoiceDomain> getFatturaByImporto(RangeDTO rangeDTO, Pageable page) {
		List<Fattura> invoicePage = fatturaRepository.findByImportoBetween(rangeDTO.getImportoMin(), rangeDTO.getImportoMax(), page)
				.getContent();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoicePage);
	}

	/**
	 * Recupera tutte le fatture in un determinato stato
	 */
	public List<InvoiceDomain> getFatturaByStato(StatoFattura stato, Pageable page) {
		List<Fattura> invoicePage = fatturaRepository.findByStatoAllIgnoreCase(stato, page)
				.getContent();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoicePage);
	}

	/**
	 * Recupera tutte le fatture per data
	 */
	public List<InvoiceDomain> getFatturaByData(DataDTO data, Pageable page) {
		List<Fattura> invoicePage = fatturaRepository.findByData(data.getData(), page)
				.getContent();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoicePage);
	}

	/**
	 * Recupera tutte le fatture per anno
	 */
	public List<InvoiceDomain> getFatturaByAnno(Integer anno, Pageable page) {
		List<Fattura> invoicePage = fatturaRepository.findByAnno(anno, page)
				.getContent();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoicePage);
	}

	/**
	 * Inserisce una Fattura nel sistema
	 */
	public InvoiceDomain createInvoice(InvoiceDomain invoiceDomain) {

		Fattura invoice = invoiceEntityMapper.fromInvoiceDomainToInvoiceEntity(invoiceDomain);
		Path root = Paths.get("upload");
		Cliente cliente = clienteRepository.findById(invoiceDomain.getCustomer().getId())
				.orElseThrow(() -> new NotCreatableException(ERROR_ONE));
		cliente.getFatture().add(invoice);
		invoice.setCliente(cliente);
		log.info("Cliente associato");

		Fattura saved = fatturaRepository.save(invoice);
		log.info("invoice id {} saved", invoice.getId());
		return invoiceEntityMapper.fromInvoiceEntityToInvoiceDomain(saved);
	}

	/**
	 * Modifica una Fattura
	 */
	public InvoiceDomain updateInvoice(InvoiceDomain invoiceDomain) throws NotFoundException {
//		if (fatturaRepository.existsById(invoiceDomain.getIdFattura())) {
		Fattura fattura = fatturaRepository.findById(invoiceDomain.getId())
				.orElseThrow(() -> new NotUpdatableException(ERROR_TWO));

		if(invoiceDomain.getCustomer().getId() != null){
			//TODO REFACT
			Cliente cliente = clienteRepository.findById(invoiceDomain.getCustomer().getId())
					.orElseThrow(() -> new NotCreatableException(ERROR_ONE));
			cliente.getFatture().add(fattura);
			fattura.setCliente(cliente);
			log.info("Cliente associato");
		}

		Fattura updated = fatturaRepository.save(fattura);
		log.info("Invoice id {} updated", updated.getId());
		return invoiceEntityMapper.fromInvoiceEntityToInvoiceDomain(updated);
//		} else {
//			throw new NotFoundException("La Fattura id " + dto.getIdFattura() + " non esiste");
//		}
	}

	//TODO IMPLEMENTARE
	public ResponseEntity<?> inserisciFatturaPDF(InvoiceAddPDFDTO invoiceAddPDFDTO) throws IOException {
		Path root = Paths.get("upload");
		File file = new File(root.toUri());
		invoiceAddPDFDTO.getFileFattura().transferTo(file);
		Fattura fattura = fatturaRepository.findById(invoiceAddPDFDTO.getIdFattura())
				.orElseThrow(() -> new NotFoundException(ERROR_ONE));
		fattura.setFile(file);
		save(invoiceAddPDFDTO.getFileFattura());
		log.info("CV SALVATO");

		return ResponseEntity.ok().build();
	}

	/**
	 * Elimina una Fattura
	 */
	public void deleteInvoice(Long id) {
		if (fatturaRepository.existsById(id)) {
			fatturaRepository.deleteById(id);
			log.info("Invoice id {} deleted", id);
		} else {
			throw new NotFoundException(ERROR_ONE);
		}
	}

}
