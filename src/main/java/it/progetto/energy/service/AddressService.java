package it.progetto.energy.service;

import it.progetto.energy.model.AddressDomain;
import it.progetto.energy.model.PageDomain;

import java.util.List;

public interface AddressService {

    List<AddressDomain> findAllIndirizziLegali();

    List<AddressDomain> findAllAddressPaged(PageDomain pageDomain);

    AddressDomain createIndirizzo(AddressDomain addressDomain);

    AddressDomain updateAddress(AddressDomain addressDomain);

    void deleteMainAddress(Long id);

}
