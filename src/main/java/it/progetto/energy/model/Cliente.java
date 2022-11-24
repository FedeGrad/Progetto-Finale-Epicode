package it.progetto.energy.model;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;
	@Column(name = "regione_sociale")
	private String ragioneSociale;
	private String partitaIva;
	private String email;
	private LocalDate dataDiNascita;
	private int anni;
	@CreationTimestamp
	private LocalDate dataInserimento;
	//@Temporal(TemporalType.DATE) solo con la classe Date
	private LocalDate dataUltimoContatto;
	private BigDecimal fatturatoAnnuale;
	@Enumerated(EnumType.STRING)
	private Tipologia tipologia;
	private String pec;
	private String telefono;
	@Email
	private String emailContatto;
	private String nomeContatto;
	private String cognomeContatto;
	private String telefonoContatto;
	// @JsonIgnore
	// @ToStringExclude
	@OneToOne(mappedBy = "cliente", cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private IndirizzoOperativo indirizzoOperativo;
	// @JsonIgnore
	// @ToStringExclude
	@OneToOne(mappedBy = "cliente", cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private IndirizzoLegale indirizzoLegale;
	@JsonIgnore
	@ToStringExclude
	@OneToMany(mappedBy = "cliente", cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE },
	fetch = FetchType.LAZY)
	private List<Fattura> fatture = new ArrayList<Fattura>();

}
