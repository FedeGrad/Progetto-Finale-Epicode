package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.CustomerDomain;
import it.progetto.energy.persistence.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {InvoiceEntityMapper.class, AddressOperationalEntityMapper.class,
        AddressMainEntityMapper.class})
public interface CustomerEntityMapper {

     @Mapping(target = "addressOperational", ignore = true)
     @Mapping(target = "addressMain", ignore = true)
     @Mapping(target = "type", source = "tipologia.tipologia")
     @Mapping(target = "surname", source = "cognomeContatto")
     @Mapping(target = "name", source = "nomeContatto")
     @Mapping(target = "invoiceList", source = "fatture")
     @Mapping(target = "dateOfBirth", source = "dataDiNascita")
     @Mapping(target = "dataLastUpdate", source = "dataUltimoContatto")
     @Mapping(target = "dataCreate", source = "dataInserimento")
     @Mapping(target = "customerPhone", source = "telefonoContatto")
     @Mapping(target = "customerEmail", source = "emailContatto")
     @Mapping(target = "companyPhone", source = "telefono")
     @Mapping(target = "companyName", source = "ragioneSociale")
     @Mapping(target = "annualTurnover", source = "fatturatoAnnuale")
     @Mapping(target = "NPI", source = "partitaIva")
     CustomerDomain fromCustomerToCustomerDomain(Cliente cliente);

     List<CustomerDomain> fromCustomerListToCustomerDomainList(List<Cliente> clienteList);

     @Mapping(target = "indirizzoOperativo", ignore = true)
     @Mapping(target = "indirizzoLegale", ignore = true)
     @Mapping(target = "tipologia", source = "type")
     @Mapping(target = "telefonoContatto", source = "customerPhone")
     @Mapping(target = "telefono", source = "companyPhone")
     @Mapping(target = "ragioneSociale", source = "companyName")
     @Mapping(target = "partitaIva", source = "NPI")
     @Mapping(target = "nomeContatto", source = "name")
     @Mapping(target = "cognomeContatto", source = "surname")
     @Mapping(target = "fatture", source = "invoiceList")
     @Mapping(target = "fatturatoAnnuale", source = "annualTurnover")
     @Mapping(target = "emailContatto", source = "customerEmail")
     @Mapping(target = "dataUltimoContatto", source = "dataLastUpdate")
     @Mapping(target = "dataInserimento", source = "dataCreate")
     @Mapping(target = "dataDiNascita", source = "dateOfBirth")
     Cliente fromCustomerDomainToCustomer(CustomerDomain customerDomain);

}
