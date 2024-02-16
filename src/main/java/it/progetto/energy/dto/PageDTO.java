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
        @Schema(name = "Sort by", defaultValue = "ASC")
        String sortDirection,
        @Schema(name = "by Value")
        String sortBy
) {
}
