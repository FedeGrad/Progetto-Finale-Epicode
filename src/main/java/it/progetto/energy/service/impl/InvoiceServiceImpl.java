package it.progetto.energy.service.impl;

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
import it.progetto.energy.persistence.repository.CustomerRepository;
import it.progetto.energy.persistence.repository.InvoiceRepository;
import it.progetto.energy.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
public class InvoiceServiceImpl implements InvoiceService {

	private final InvoiceRepository invoiceRepository;
	private final CustomerRepository customerRepository;
	private final InvoiceEntityMapper invoiceEntityMapper;
	private final FileServiceImpl fileService;

	/**
	 * Recupera tutte le fatture
	 * @deprecated
	 */
	@Deprecated
	@Override
	public List<InvoiceDomain> findAllInvoice() {
		List<Fattura> invoiceList = invoiceRepository.findAll();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoiceList);
	}

	/**
	 * Recupera tutte le fatture, paginate
	 */
	@Override
	public List<InvoiceDomain> findAllInvoice(Pageable page) {
		List<Fattura> invoicePage = invoiceRepository.findAll(page)
				.getContent();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoicePage);
	}

	/**
	 * Recupera tutte le fatture di un Cliente
	 */
	@Override
	public List<InvoiceDomain> findInvoiceByCustomer(Long customerId) {
		List<Fattura> invoiceList = invoiceRepository.findByCliente(customerId);
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoiceList);
	}

	/**
	 * Recupera tutte le fatture di un determinato importo
	 */
	@Override
	public List<InvoiceDomain> findInvoiceByAmountBetween(RangeDTO rangeDTO, Pageable page) {
		List<Fattura> invoicePage = invoiceRepository.findByImportoBetween(rangeDTO.getImportoMin(), rangeDTO.getImportoMax(), page)
				.getContent();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoicePage);
	}

	/**
	 * Recupera tutte le fatture in un determinato stato
	 */
	@Override
	public List<InvoiceDomain> findInvoiceByState(StatoFattura stato, Pageable page) {
		List<Fattura> invoicePage = invoiceRepository.findByStatoAllIgnoreCase(stato, page)
				.getContent();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoicePage);
	}

	/**
	 * Recupera tutte le fatture per data
	 */
	@Override
	public List<InvoiceDomain> findInvoiceByDate(DataDTO data, Pageable page) {
		List<Fattura> invoicePage = invoiceRepository.findByData(data.getData(), page)
				.getContent();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoicePage);
	}

	/**
	 * Recupera tutte le fatture per anno
	 */
	@Override
	public List<InvoiceDomain> findInvoiceByYear(Integer year, Pageable page) {
		List<Fattura> invoicePage = invoiceRepository.findByAnno(year, page)
				.getContent();
		return invoiceEntityMapper.fromInvoiceEntityListToInvoiceDomainList(invoicePage);
	}

	/**
	 * Inserisce una Fattura nel sistema
	 */
	@Override
	public InvoiceDomain createInvoice(InvoiceDomain invoiceDomain) {
		Fattura invoice = invoiceEntityMapper.fromInvoiceDomainToInvoiceEntity(invoiceDomain);
		Cliente cliente = customerRepository.findById(invoiceDomain.getCustomer().getId())
				.orElseThrow(() -> new NotCreatableException(ERROR_ONE));
		cliente.getFatture().add(invoice);
		invoice.setCliente(cliente);
		log.info("Cliente associato");

		Fattura saved = invoiceRepository.save(invoice);
		log.info("invoice id {} saved", invoice.getId());
		return invoiceEntityMapper.fromInvoiceEntityToInvoiceDomain(saved);
	}

	/**
	 * Modifica una Fattura
	 */
	@Override
	public InvoiceDomain updateInvoice(InvoiceDomain invoiceDomain) {
//		if (fatturaRepository.existsById(invoiceDomain.getIdFattura())) {
		Fattura fattura = invoiceRepository.findById(invoiceDomain.getId())
				.orElseThrow(() -> new NotUpdatableException(ERROR_TWO));

		if(invoiceDomain.getCustomer().getId() != null){
			//TODO REFACT
			Cliente cliente = customerRepository.findById(invoiceDomain.getCustomer().getId())
					.orElseThrow(() -> new NotCreatableException(ERROR_ONE));
			cliente.getFatture().add(fattura);
			fattura.setCliente(cliente);
			log.info("Cliente associato");
		}

		Fattura updated = invoiceRepository.save(fattura);
		log.info("Invoice id {} updated", updated.getId());
		return invoiceEntityMapper.fromInvoiceEntityToInvoiceDomain(updated);
//		} else {
//			throw new NotFoundException("La Fattura id " + dto.getIdFattura() + " non esiste");
//		}
	}

	//TODO IMPLEMENTARE
	@Override
	public void uploadInvoicePDF(InvoiceAddPDFDTO invoiceAddPDFDTO) throws IOException {
		Path root = Paths.get("upload");
		File file = new File(root.toUri());
		invoiceAddPDFDTO.getFileFattura()
				.transferTo(file);

		Fattura fattura = invoiceRepository.findById(invoiceAddPDFDTO.getIdFattura())
				.orElseThrow(() -> new NotFoundException(ERROR_ONE));
		fattura.setFile(file);
		fileService.save(invoiceAddPDFDTO.getFileFattura());
		log.info("PDF upload");
	}

	/**
	 * Elimina una Fattura
	 */
	@Override
	public void deleteInvoice(Long id) {
		if (invoiceRepository.existsById(id)) {
			invoiceRepository.deleteById(id);
			log.info("Invoice id {} deleted", id);
		} else {
			throw new NotFoundException(ERROR_ONE);
		}
	}

}
