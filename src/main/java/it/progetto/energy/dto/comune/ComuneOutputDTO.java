package it.progetto.energy.dto.comune;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComuneOutputDTO {

    private Long id;

    private String name;

    private String postalCode;

    private Long provinciaId;

    private List<Long> addressIdList;

}
