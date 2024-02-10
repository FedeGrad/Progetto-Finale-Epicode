package it.progetto.energy.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteOutputDTO {

    private Long id;
    private String ragioneSociale;
    private String partitaIva;
    private String email;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private BigDecimal fatturatoAnnuale;
    private String tipologia;
    private String pec;
    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private LocalDate dataDiNascita;
    private String telefonoContatto;
    private Long idIndirizzoOperativo;
    private Long idIndirizzoLegale;
}
