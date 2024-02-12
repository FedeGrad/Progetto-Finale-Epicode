package it.progetto.energy.service;

import it.progetto.energy.model.ComuneDomain;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComuneService {

    List<ComuneDomain> findAllComuni();

    List<ComuneDomain> findAllComuni(Pageable page);

    ComuneDomain createComune(ComuneDomain comuneDomain);

    ComuneDomain updateComune(ComuneDomain comuneDomain);

    void deleteComune(Long id);

}
