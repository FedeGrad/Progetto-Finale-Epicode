package it.progetto.energy.model;

public enum StatoFattura {
	PAGATA("pagata"),
	NON_PAGATA("non pagata"),
	ANNULLATA("annullata"),
	SCADUTA("scaduta"),
	DA_RIMBORSARE("da rimborsare"),
	RIMBORSATA("rimborsata");

	String stato;

	private StatoFattura(String stato) {
		this.stato = stato;
	}

}
