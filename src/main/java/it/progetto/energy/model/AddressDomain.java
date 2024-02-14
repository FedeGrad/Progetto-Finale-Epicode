package it.progetto.energy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AddressDomain {

    private Long id;

    private String way;

    private String number;

    private String postalCode;

    private ComuneDomain comune;

    private List<CustomerDomain> customerList;

}
