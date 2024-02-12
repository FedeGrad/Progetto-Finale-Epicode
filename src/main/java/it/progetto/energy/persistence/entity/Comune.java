package it.progetto.energy.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comune {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;

	private String nome;

	private String cap;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
//	@ManyToOne
	@JoinColumn(name = "id_provincia")
	private Provincia provincia;

	@JsonIgnore @ToString.Exclude
	@OneToMany(mappedBy = "comune")
	private List<IndirizzoOperativo> indirizziOperativi;

	@JsonIgnore @ToString.Exclude
	@OneToMany(mappedBy = "comune")
	private List<IndirizzoLegale> indirizziLegali;

}
