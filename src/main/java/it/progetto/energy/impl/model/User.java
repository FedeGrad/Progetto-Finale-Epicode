package it.progetto.energy.impl.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Data
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
	private String username;

	@NotBlank
	@Size(max = 120)
	private String password;
	
	@Size(max = 120)
	private String nome;
	
	@Size(max = 120)
	private String cognome;
	
	@NotBlank
	@Size(max = 120)
	private String email;
	
	private boolean accountAttivo = false;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST } )
	@JoinTable(name = "user_roles", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleAccess> roles = new HashSet<RoleAccess>();
	
	public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password,
				@NotBlank @Size(max = 120) String email, @NotBlank @Size(max = 120) String nome,
				@NotBlank @Size(max = 120) String cognome) {
		super();
		this.username = username;
		this.password = password;
		this.nome =nome;
		this.cognome = cognome;
		this.email = email;
	}

}
