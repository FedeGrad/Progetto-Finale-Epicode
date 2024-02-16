package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.invoice.InvoiceOutputDTO;
import it.progetto.energy.model.CustomerDomain;
import it.progetto.energy.model.InvoiceDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.DomainBuilder.buildInvoiceDomain;
import static it.progetto.energy.utils.dtobuilder.InvoiceDTOBuilder.buildInvoiceDTO;
import static it.progetto.energy.utils.dtobuilder.InvoiceDTOBuilder.buildInvoiceOutputDTO;
import static it.progetto.energy.utils.dtobuilder.InvoiceDTOBuilder.buildInvoiceUpdateDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class InvoiceDTOMapperTest {

    @Autowired
    InvoiceDTOMapper invoiceDTOMapper;

    @Test
    void fromInvoiceDTOToInvoiceDomain() {
        var invoiceDTO = buildInvoiceDTO();
        var customerDomain = CustomerDomain.builder().id(ENTITY_ID).build();
        var expected = buildInvoiceDomain(null);
        expected.setCustomer(customerDomain);

        InvoiceDomain actual = invoiceDTOMapper.fromInvoiceDTOToInvoiceDomain(invoiceDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromInvoiceUpdateDTOToInvoiceDomain() {
        var invoiceDTO = buildInvoiceUpdateDTO();
        var customerDomain = CustomerDomain.builder().id(ENTITY_ID).build();
        var expected = buildInvoiceDomain(ENTITY_ID);
        expected.setCustomer(customerDomain);

        InvoiceDomain actual = invoiceDTOMapper.fromInvoiceUpdateDTOToInvoiceDomain(invoiceDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromInvoiceDomainToInvoiceOutputDTO() {
        var customerDomain = CustomerDomain.builder().id(ENTITY_ID).build();
        var invoiceDomain = buildInvoiceDomain(ENTITY_ID);
        invoiceDomain.setCustomer(customerDomain);
        var expected = buildInvoiceOutputDTO();

        InvoiceOutputDTO actual = invoiceDTOMapper.fromInvoiceDomainToInvoiceOutputDTO(invoiceDomain);

        assertEquals(expected, actual);
    }

    @Test
    void fromInvoiceListDomainToInvoiceOutputDTOList() {
        var customerDomain = CustomerDomain.builder().id(ENTITY_ID).build();
        var invoiceDomain = buildInvoiceDomain(ENTITY_ID);
        invoiceDomain.setCustomer(customerDomain);
        var invoiceDomainList = List.of(invoiceDomain);
        var expected = List.of(buildInvoiceOutputDTO());

        List<InvoiceOutputDTO> actual = invoiceDTOMapper.fromInvoiceListDomainToInvoiceOutputDTOList(invoiceDomainList);

        assertEquals(expected, actual);
    }

}