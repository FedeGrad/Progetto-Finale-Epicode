package it.progetto.energy.dto.user;

import it.progetto.energy.model.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

	private Long id;

	@Size(max = 120)
	private String nome;

	@Size(max = 120)
	private String cognome;

	@Size(max = 20)
	private String username;
	
	@Email
	private String email;
	
	private List<Role> roleList;

}
