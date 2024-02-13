package it.progetto.energy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ProvinciaDomain {

    private Long id;

    private String sigla;

    private String name;

    private String region;

    private List<ComuneDomain> comuneList;

}
