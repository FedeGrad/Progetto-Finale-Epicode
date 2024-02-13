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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Objects;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;

	@Column(name = "way")
	private String way;

	@Column(name = "number")
	private String number;

	@Column(name = "location")
	private String location;

	@Column(name = "postal_code")
	private String postalCode;

	@JsonIgnore
	@ToStringExclude
//	@ManyToOne(cascade = { CascadeType.MERGE })
	@ManyToOne
	@JoinColumn(name = "id_comune", referencedColumnName = "id")
	private ComuneEntity comune;

	@JsonIgnore
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
	private CustomerEntity customer;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AddressEntity)) return false;
		AddressEntity addressEntity = (AddressEntity) o;
		return Objects.equals(id, addressEntity.id) && Objects.equals(way, addressEntity.way)
				&& Objects.equals(number, addressEntity.number) && Objects.equals(location, addressEntity.location)
				&& Objects.equals(postalCode, addressEntity.postalCode)
				&& Objects.equals(comune, addressEntity.comune)
				&& Objects.equals(customer, addressEntity.customer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, way, number, location, postalCode, comune, customer);
	}

}
