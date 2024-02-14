package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.progetto.energy.dto.PageDTO;
import it.progetto.energy.dto.user.UserDTO;
import it.progetto.energy.dto.user.UserOutputDTO;
import it.progetto.energy.dto.user.UserUpdateDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserApi {

    @Operation(summary = "Recupero Utenti",
            description = "Restituisce gli Utenti presenti nel sistema")
    @ApiResponse(responseCode = "200", description = "Utenti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Utente trovato")
    List<UserOutputDTO> findAllUser();

    @Operation(summary = "Recupero Utenti per pagina",
            description = "Restituisce gli Utenti presenti nel sistema per pagina")
    @ApiResponse(responseCode = "200", description = "Utenti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Utente trovato")
    List<UserOutputDTO> findAllUserPaged(PageDTO pageDTO);

    @Operation(summary = "Inserimento Utente",
            description = "Inserisce un Utente nel sistema")
    @ApiResponse(responseCode = "200", description = "Utente inserito correttamente nel sistema")
    @ApiResponse(responseCode = "400", description = "Utente gia presente nel sistema")
    UserOutputDTO createUser(UserDTO userDTO);

    @Operation(summary = "Modifica Utente",
            description = "Modifica un Utente presente nel sistema")
    @ApiResponse(responseCode = "200", description = "Utente modificato")
    @ApiResponse(responseCode = "404", description = "Utente non trovato")
    UserOutputDTO updateUser(@RequestBody UserUpdateDTO updateDTO);

    @Operation(summary = "Eliminazione Utente",
            description = "Elimina un Utente presente nel sistema")
    @ApiResponse(responseCode = "204", description = "Utente eliminato")
    @ApiResponse(responseCode = "404", description = "Utente non trovato")
    void deleteUser(@PathVariable("id") Long userId);

}
