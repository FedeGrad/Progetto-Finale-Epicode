package it.progetto.energy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record PageDTO(
        @NotNull
        @Schema(type = "int", defaultValue = "0")
        int page,
        @NotNull
        @Schema(type = "int", defaultValue = "100")
        int size,
        @NotNull
//        @Schema(type = "string", example = "ASC", defaultValue = "ASC")
        String sortDirection,
//        @Schema(type = "string", defaultValue = "id")
        String sortBy
) {
}
