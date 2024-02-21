package it.progetto.energy.dto.csv;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CsvImportedOutputDTO(

        Long numberComuni,

        Long numberProvince

) {
}
