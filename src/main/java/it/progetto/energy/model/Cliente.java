package it.progetto.energy.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;
	private String ragioneSociale;
	private String partitaIva;
	private String email;
	private LocalDate dataInserimento;
	private LocalDate dataUltimoContatto;
	private BigDecimal fatturatoAnnuale;
	@Enumerated(EnumType.STRING)
	private Tipologia tipologia;
	private String pec;
	private String telefono;
	private String emailContatto;
	private String nomeContatto;
	private String cognomeContatto;
	private String telefonoContatto;
	// @JsonIgnore
	// @ToStringExclude
	@OneToOne(mappedBy = "cliente", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private IndirizzoOperativo indirizzoOperativo;
	// @JsonIgnore
	// @ToStringExclude
	@OneToOne(mappedBy = "cliente", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private IndirizzoLegale indirizzoLegale;
	@JsonIgnore
	@ToStringExclude
	@OneToMany(mappedBy = "cliente", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private List<Fattura> fatture = new ArrayList<Fattura>();

}
