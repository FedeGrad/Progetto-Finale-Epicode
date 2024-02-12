package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.customer.CustomerDTO;
import it.progetto.energy.dto.customer.CustomerOutputDTO;
import it.progetto.energy.dto.customer.CustomerUpdateDTO;
import it.progetto.energy.model.CustomerDomain;
import it.progetto.energy.model.InvoiceDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {InvoiceDTOMapper.class, Page.class})
public interface CustomerDTOMapper {

     @Mapping(target = "invoiceIdList", source = "invoiceList", qualifiedByName = "fromInvoiceToInvoiceId")
     @Mapping(target = "mainAddressId", source = "addressMain.id")
     CustomerOutputDTO fromCustomerDomainToCustomerOutputDTO(CustomerDomain customerDomainDomain);

     List<CustomerOutputDTO> fromCustomerDomainListToCustomerOutputDTOList(List<CustomerDomain> customerDomainDomain);

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "anni", ignore = true)
     @Mapping(target = "addressMain.id", source = "addressMainId")
     @Mapping(target = "invoiceList", source = "invoiceIdList", qualifiedByName = "fromInvoiceIdToInvoice")
     CustomerDomain fromCustomerUpdateDTOToCustomerDomain(CustomerDTO customerDTO);

     @Mapping(target = "dataCreate", ignore = true)
     @Mapping(target = "anni", ignore = true)
     @Mapping(target = "dataLastUpdate", ignore = true)
     @Mapping(target = "addressMain.id", source = "addressMainId")
     @Mapping(target = "invoiceList", source = "invoiceIdList", qualifiedByName = "fromInvoiceIdToInvoice")
     CustomerDomain fromCustomerUpdateDTOToCustomerDomain(CustomerUpdateDTO customerDTO);

     @Named("fromInvoiceToInvoiceId")
     default List<Long> fromInvoiceToInvoiceId(List<InvoiceDomain> invoiceDomainList){
          if(!invoiceDomainList.isEmpty()){
               return invoiceDomainList.stream()
                       .map(InvoiceDomain::getId)
                       .collect(Collectors.toList());
          }
          return Collections.emptyList();
     }

     @Named("fromInvoiceIdToInvoice")
     default List<InvoiceDomain> fromInvoiceIdToInvoice(List<Long> invoiceIdList){
          if (!invoiceIdList.isEmpty()){
               List<InvoiceDomain> invoiceDomainList = new ArrayList<>();
               invoiceIdList.forEach(invoiceId -> {
                    InvoiceDomain invoice = InvoiceDomain.builder()
                            .id(invoiceId)
                            .build();
                    invoiceDomainList.add(invoice);
               });
               return invoiceDomainList;
          }
          return Collections.emptyList();
     }

}
