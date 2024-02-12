package it.progetto.energy.service;

import it.progetto.energy.model.AddressDomain;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {

    List<AddressDomain> findAllIndirizziLegali();

    List<AddressDomain> findAllIndirizziLegali(Pageable page);

    AddressDomain createIndirizzo(AddressDomain addressDomain);

    AddressDomain updateAddress(AddressDomain addressDomain);

    void deleteMainAddress(Long id);

}
