package it.progetto.energy.service;

import it.progetto.energy.model.ProvinciaDomain;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProvinciaService {

    List<ProvinciaDomain> findAllProvince();

    List<ProvinciaDomain> findAllProvince(Pageable page);

    ProvinciaDomain createProvincia(ProvinciaDomain provinciaDomain);

    ProvinciaDomain updateProvincia(ProvinciaDomain provinciaDomain);

    void deleteProvincia(Long id);
}
