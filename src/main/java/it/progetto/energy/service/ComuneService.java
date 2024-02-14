package it.progetto.energy.service;

import it.progetto.energy.model.ComuneDomain;
import it.progetto.energy.model.PageDomain;

import java.util.List;

public interface ComuneService {

    List<ComuneDomain> findAllComuni();

    List<ComuneDomain> findAllComuni(PageDomain pageDomain);

    ComuneDomain createComune(ComuneDomain comuneDomain);

    ComuneDomain updateComune(ComuneDomain comuneDomain);

    void deleteComune(Long id);

}
