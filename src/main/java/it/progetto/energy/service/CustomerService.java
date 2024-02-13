package it.progetto.energy.service;

import it.progetto.energy.model.CustomerDomain;
import it.progetto.energy.model.DataDomain;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    List<CustomerDomain> findAllCustomer();

    List<CustomerDomain> findAllCustomer(Pageable page);

    List<CustomerDomain> findCustomerByName(String name, Pageable page);

    List<CustomerDomain> findCustomerByNameContain(String nomeContiene, Pageable page);

    List<CustomerDomain> findCustomerByAnnualTurnover(Double fatturato, Pageable page);

    List<CustomerDomain> findCustomerByDataCreate(DataDomain dataCreate, Pageable page);

    List<CustomerDomain> findCustomerByDataLastUpdate(DataDomain dataLastUpdate, Pageable page);

    List<CustomerDomain> findCustomerByProvincia(Long provinciaId);

    CustomerDomain createCustomer(CustomerDomain customerDomain);

    CustomerDomain updateCustomer(CustomerDomain customerDomain);

    void deleteCustomer(Long id);

}
