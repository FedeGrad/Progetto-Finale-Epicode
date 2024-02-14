package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.model.CustomerDomain;
import it.progetto.energy.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {InvoiceEntityMapper.class})
public interface CustomerEntityMapper {

     @Mapping(target = "address", ignore = true)
     CustomerDomain fromCustomerToCustomerDomain(CustomerEntity customerEntity);

     List<CustomerDomain> fromCustomerListToCustomerDomainList(List<CustomerEntity> customerEntityList);

     @Mapping(target = "address", ignore = true)
     CustomerEntity fromCustomerDomainToCustomer(CustomerDomain customerDomain);

     default List<CustomerDomain> fromCustomerEntityToCustomerDomainList(CustomerEntity customerEntity) {
          if(customerEntity!=null){
               return List.of(fromCustomerToCustomerDomain(customerEntity));
          }
          return Collections.emptyList();
     }

}
