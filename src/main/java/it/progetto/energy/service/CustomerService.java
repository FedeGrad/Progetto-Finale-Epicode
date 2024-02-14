package it.progetto.energy.service;

import it.progetto.energy.model.CustomerDomain;
import it.progetto.energy.model.DataDomain;
import it.progetto.energy.model.PageDomain;

import java.util.List;

public interface CustomerService {

    List<CustomerDomain> findAllCustomer();

    List<CustomerDomain> findAllCustomerPaged(PageDomain pageDomain);

    List<CustomerDomain> findCustomerByName(String name, PageDomain pageDomain);

    List<CustomerDomain> findCustomerByNameContain(String nomeContiene, PageDomain pageDomain);

    List<CustomerDomain> findCustomerByAnnualTurnover(Double fatturato, PageDomain pageDomain);

    List<CustomerDomain> findCustomerByDataCreate(DataDomain dataCreate, PageDomain pageDomain);

    List<CustomerDomain> findCustomerByDataLastUpdate(DataDomain dataLastUpdate, PageDomain pageDomain);

    List<CustomerDomain> findCustomerByProvincia(Long provinciaId);

    CustomerDomain createCustomer(CustomerDomain customerDomain);

    CustomerDomain updateCustomer(CustomerDomain customerDomain);

    void deleteCustomer(Long id);

}
