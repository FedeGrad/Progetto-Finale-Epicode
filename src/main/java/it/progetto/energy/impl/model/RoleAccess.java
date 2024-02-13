package it.progetto.energy.impl.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "roles")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleAccess {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "role_name", length = 20)
	private ERoleAccess roleName;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RoleAccess that)) return false;
        return Objects.equals(id, that.id) && roleName == that.roleName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, roleName);
	}

}
