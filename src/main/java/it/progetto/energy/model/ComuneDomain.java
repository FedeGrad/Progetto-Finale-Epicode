package it.progetto.energy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ComuneDomain {

    private Long id;

    private String name;

    private String postalCode;

    private ProvinciaDomain provincia;

    private List<AddressDomain> addressDomainList;
    
}
