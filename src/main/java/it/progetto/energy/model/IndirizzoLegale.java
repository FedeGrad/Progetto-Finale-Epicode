package it.progetto.energy.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndirizzoLegale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;
	private String via;
	private String civico;
	private String localita;
	private String cap;
	@JsonIgnore
	@ToStringExclude
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	// @ManyToOne
	@JoinColumn(name = "id_comune", referencedColumnName = "id")
	private Comune comune;
	@JsonIgnore
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Cliente cliente;

}
