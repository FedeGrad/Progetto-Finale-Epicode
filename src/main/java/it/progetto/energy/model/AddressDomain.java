package it.progetto.energy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AddressDomain {

    private Long id;

    private String way;

    private String number;

    private String postalCode;

    private ComuneDomain comune;

    private CustomerDomain customer;

}
