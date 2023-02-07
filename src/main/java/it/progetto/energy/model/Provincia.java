package it.progetto.energy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provincia {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;
	private String sigla;
	private String nome;
	private String regione;
	@JsonIgnore @ToString.Exclude
	@OneToMany(mappedBy = "provincia",
			cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH })
	private List<Comune> comuni = new ArrayList<Comune>();

}
