package it.progetto.energy.dto;

import lombok.Builder;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;

@Builder
public record PageDTO(
        @NotNull
        int page,
        @NotNull
        int size,
        @NotNull
        Sort.Direction direction
) {
}
