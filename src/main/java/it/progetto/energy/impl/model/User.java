package it.progetto.energy.impl.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Long id;

	@NotBlank
	@Size(max = 20)
	@Column(name = "username")
	private String username;

	@NotBlank
	@Size(max = 120)
	@Column(name = "password")
	private String password;
	
	@Size(max = 120)
	@Column(name = "name")
	private String name;
	
	@Size(max = 120)
	@Column(name = "surname")
	private String surname;
	
	@NotBlank
	@Size(max = 120)
	@Column(name = "email")
	private String email;

	@Column(name = "active")
	private boolean accountAttivo = false;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name = "user_roles",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleAccess> roles = new HashSet<RoleAccess>();
	
	public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password,
				@NotBlank @Size(max = 120) String email, @NotBlank @Size(max = 120) String name,
				@NotBlank @Size(max = 120) String surname) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return accountAttivo == user.accountAttivo && Objects.equals(id, user.id)
				&& Objects.equals(username, user.username) && Objects.equals(password, user.password)
				&& Objects.equals(name, user.name) && Objects.equals(surname, user.surname)
				&& Objects.equals(email, user.email) && Objects.equals(roles, user.roles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, name, surname, email, accountAttivo, roles);
	}

}
