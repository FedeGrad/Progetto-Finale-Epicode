package it.progetto.energy.mapper;

import it.progetto.energy.dto.cliente.CustomerOutputDTO;
import it.progetto.energy.persistence.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

     @Mapping(target = "idIndirizzoOperativo", source = "indirizzoOperativo.id")
     @Mapping(target = "idIndirizzoLegale", source = "indirizzoLegale.id")
     CustomerOutputDTO fromDomainToOutput(Cliente customerDomainDomain);

}
