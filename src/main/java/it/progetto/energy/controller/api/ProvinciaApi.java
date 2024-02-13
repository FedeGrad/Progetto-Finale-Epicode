package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.progetto.energy.dto.provincia.ProvinciaOutputDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProvinciaApi {

    @Operation(summary = "Recupero Province",
            description = "Restituisce tutte le Province presenti nel sistema")
    @ApiResponse(responseCode = "200", description = "Province trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Provincia trovata")
    List<ProvinciaOutputDTO> findAllProvince();

    @Operation(summary = "Recupero Province",
            description = "Restituisce tutte le Province presenti nel sistema per pagina")
    @ApiResponse(responseCode = "200", description = "Province trovate")
    @ApiResponse(responseCode = "404", description = "Nessuna Provincia trovata")
    List<ProvinciaOutputDTO> findAllProvince(Pageable page);

}
