package it.progetto.energy.service;

import it.progetto.energy.model.PageDomain;
import it.progetto.energy.model.ProvinciaDomain;

import java.util.List;

public interface ProvinciaService {

    List<ProvinciaDomain> findAllProvince();

    List<ProvinciaDomain> findAllProvincePaged(PageDomain pageDomain);

    ProvinciaDomain createProvincia(ProvinciaDomain provinciaDomain);

    ProvinciaDomain updateProvincia(ProvinciaDomain provinciaDomain);

    void deleteProvincia(Long id);
}
