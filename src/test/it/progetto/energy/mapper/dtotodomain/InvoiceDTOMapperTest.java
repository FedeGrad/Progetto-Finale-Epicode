package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.invoice.InvoiceOutputDTO;
import it.progetto.energy.model.InvoiceDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.progetto.energy.utils.ConstantForTest.ENTITY_ID;
import static it.progetto.energy.utils.domainbuilder.InvoiceDomainBuilder.buildInvoiceDomainInput;
import static it.progetto.energy.utils.domainbuilder.InvoiceDomainBuilder.buildInvoiceDomainOutput;
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
        var expected = buildInvoiceDomainInput(null);

        InvoiceDomain actual = invoiceDTOMapper.fromInvoiceDTOToInvoiceDomain(invoiceDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromInvoiceUpdateDTOToInvoiceDomain() {
        var invoiceUpdateDTO = buildInvoiceUpdateDTO();
        var expected = buildInvoiceDomainInput(ENTITY_ID);

        InvoiceDomain actual = invoiceDTOMapper.fromInvoiceUpdateDTOToInvoiceDomain(invoiceUpdateDTO);

        assertEquals(expected, actual);
    }

    @Test
    void fromInvoiceDomainToInvoiceOutputDTO() {
        var invoiceDomain = buildInvoiceDomainOutput(ENTITY_ID);
        var expected = buildInvoiceOutputDTO();

        InvoiceOutputDTO actual = invoiceDTOMapper.fromInvoiceDomainToInvoiceOutputDTO(invoiceDomain);

        assertEquals(expected, actual);
    }

    @Test
    void fromInvoiceListDomainToInvoiceOutputDTOList() {
        var invoiceDomain = buildInvoiceDomainOutput(ENTITY_ID);
        var invoiceDomainList = List.of(invoiceDomain);
        var expected = List.of(buildInvoiceOutputDTO());

        List<InvoiceOutputDTO> actual = invoiceDTOMapper.fromInvoiceListDomainToInvoiceOutputDTOList(invoiceDomainList);

        assertEquals(expected, actual);
    }

}