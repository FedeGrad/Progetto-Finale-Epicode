package it.progetto.energy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatoFattura {

	PAGATA("pagata"),
	NON_PAGATA("non pagata"),
	ANNULLATA("annullata"),
	SCADUTA("scaduta"),
	DA_RIMBORSARE("da rimborsare"),
	RIMBORSATA("rimborsata");

	private final String stato;

}
