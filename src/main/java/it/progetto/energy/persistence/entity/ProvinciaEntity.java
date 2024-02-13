package it.progetto.energy.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity(name = "provincia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvinciaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;

	@Column(name = "sigla")
	private String sigla;

	@Column(name = "name")
	private String name;

	@Column(name = "region")
	private String region;

	@JsonIgnore @ToString.Exclude
	@OneToMany(mappedBy = "provincia",
			cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
	private List<ComuneEntity> comuneList;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProvinciaEntity)) return false;
		ProvinciaEntity provinciaEntity = (ProvinciaEntity) o;
		return Objects.equals(id, provinciaEntity.id) && Objects.equals(sigla, provinciaEntity.sigla)
				&& Objects.equals(name, provinciaEntity.name) && Objects.equals(region, provinciaEntity.region)
				&& Objects.equals(comuneList, provinciaEntity.comuneList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, sigla, name, region, comuneList);
	}

}
