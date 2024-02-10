package it.progetto.energy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tipologia {

	PA("PA"),
	SAS("SAS"),
	SPA("SPA"),
	SRL("SRL");

	private final String tipologia;

}
