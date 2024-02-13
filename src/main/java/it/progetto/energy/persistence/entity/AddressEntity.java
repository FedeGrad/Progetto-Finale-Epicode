package it.progetto.energy.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity(name = "address")
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
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
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
