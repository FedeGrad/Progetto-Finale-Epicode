package it.progetto.energy.model;

import java.io.File;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jdk.jfr.Percentage;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fattura {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;
	private Integer anno;
	private LocalDate data;
	private Double importo;
	private Integer numero;
	private File file;
	private Double importoIVA;
	@Percentage
	private Double percentualeIVA;
	private Double importoSconto;
	@Percentage
	private Double percentualeSconto;

	@Enumerated(EnumType.STRING)
	private StatoFattura stato;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH  })
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

}
