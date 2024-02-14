package it.progetto.energy.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "comune")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComuneEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "postal_code")
	private String postalCode;

	@ManyToOne(cascade = {CascadeType.DETACH})
//	@ManyToOne
	@JoinColumn(name = "id_provincia")
	private ProvinciaEntity provincia;

	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "comune", cascade = CascadeType.DETACH)
	private List<AddressEntity> addressList;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ComuneEntity comuneEntity)) return false;
        return Objects.equals(id, comuneEntity.id) && Objects.equals(name, comuneEntity.name)
				&& Objects.equals(postalCode, comuneEntity.postalCode)
				&& Objects.equals(provincia, comuneEntity.provincia)
				&& Objects.equals(addressList, comuneEntity.addressList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, postalCode, provincia, addressList);
	}

	@Override
	public String toString() {
		return "ComuneEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", postalCode='" + postalCode + '\'' +
				", provincia=" + provincia +
				", addressList=" + addressList +
				'}';
	}

}
