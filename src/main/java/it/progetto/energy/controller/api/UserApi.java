package it.progetto.energy.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.progetto.energy.dto.user.UserDTO;
import it.progetto.energy.exception.ElementAlreadyPresentException;
import it.progetto.energy.impl.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserApi {

    @Operation(summary = "Recupero Utenti",
            description = "Restituisce gli Utenti presenti nel sistema")
    @ApiResponse(responseCode = "200", description = "Utenti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Utente trovato")
    List<User> findAllUser();

    @Operation(summary = "Recupero Utenti per pagina",
            description = "Restituisce gli Utenti presenti nel sistema per pagina")
    @ApiResponse(responseCode = "200", description = "Utenti trovati")
    @ApiResponse(responseCode = "404", description = "Nessun Utente trovato")
    Page<User> findAllUser(Pageable page);

    @Operation(summary = "Inserimento Utente",
            description = "Inserisce un Utente nel sistema")
    @ApiResponse(responseCode = "200", description = "Utente inserito correttamente nel sistema")
    @ApiResponse(responseCode = "400", description = "Utente gia presente nel sistema")
    void createUser(UserDTO userDTO) throws ElementAlreadyPresentException;

    @Operation(summary = "Modifica Utente",
            description = "Modifica un Utente presente nel sistema")
    @ApiResponse(responseCode = "200", description = "Utente modificato")
    @ApiResponse(responseCode = "404", description = "Utente non trovato")
    void updateUser(@RequestBody UserDTO updateDTO);

    @Operation(summary = "Eliminazione Utente",
            description = "Elimina un Utente presente nel sistema")
    @ApiResponse(responseCode = "204", description = "Utente eliminato")
    @ApiResponse(responseCode = "404", description = "Utente non trovato")
    void deleteUser(@PathVariable("id") Long userId);

}
