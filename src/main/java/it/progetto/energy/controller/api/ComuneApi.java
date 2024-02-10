package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.progetto.energy.persistence.entity.Comune;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComuneApi {

    @Operation(summary = "Recupero Comuni",
            description = "Restituisce tutti i Comuni presenti nel sistema")
    @ApiResponse(responseCode = "200", description = "Comuni trovati")
    @ApiResponse(responseCode = "404", description = "Nessuna Comune trovato")
    List<Comune> findAllComuni();

    @Operation(summary = "Recupero Comuni per pagina",
            description = "Restituisce tutti i Comuni presenti nel sistema per pagina")
    @ApiResponse(responseCode = "200", description = "Comuni trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Comune trovato")
    Page<Comune> findAllComuni(Pageable page);

}
