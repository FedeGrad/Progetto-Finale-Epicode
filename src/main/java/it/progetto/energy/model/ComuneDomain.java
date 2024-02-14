package it.progetto.energy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComuneDomain {

    private Long id;

    private String name;

    private String postalCode;

    private ProvinciaDomain provincia;

    private List<AddressDomain> addressList;
    
}
