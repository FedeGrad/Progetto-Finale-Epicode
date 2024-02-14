package it.progetto.energy.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "provincia")
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
	@OneToMany(mappedBy = "provincia", cascade = {CascadeType.DETACH})
	private List<ComuneEntity> comuneList;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProvinciaEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(sigla, that.sigla) && Objects.equals(name, that.name)
				&& Objects.equals(region, that.region) && Objects.equals(comuneList, that.comuneList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, sigla, name, region, comuneList);
	}

	@Override
	public String toString() {
		return "ProvinciaEntity{" +
				"id=" + id +
				", sigla='" + sigla + '\'' +
				", name='" + name + '\'' +
				", region='" + region + '\'' +
				", comuneList=" + comuneList +
				'}';
	}

}
