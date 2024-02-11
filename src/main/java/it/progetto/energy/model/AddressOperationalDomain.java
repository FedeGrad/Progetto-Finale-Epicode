package it.progetto.energy.model;

import it.progetto.energy.persistence.entity.Cliente;
import it.progetto.energy.persistence.entity.Comune;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AddressOperationalDomain {

    private Long id;

    private String via;

    private String civico;

    private String localita;

    private String cap;

    private Comune comune;

    private Cliente cliente;

}
