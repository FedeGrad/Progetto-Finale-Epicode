package it.progetto.energy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Comune {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;
	private String nome;
	private String cap;
	//	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
//	@ManyToOne
	@JoinColumn(name = "id_provincia")
	private Provincia provincia;
	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "comune")
	private List<IndirizzoOperativo> indirizziOperativi = new ArrayList<IndirizzoOperativo>();
	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "comune")
	private List<IndirizzoLegale> indirizziLegali = new ArrayList<IndirizzoLegale>();

}
