package it.progetto.energy.dto.user;

import it.progetto.energy.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	@NotBlank
	@Size(max = 120)
	private String nome;

	@NotBlank
	@Size(max = 120)
	private String cognome;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(min = 2, max = 120)
	@Pattern(regexp = "^(?=.*[\\d'])(?=.*[!@#$%^&*(),.?\":{}|<>])[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>]*$",
			message = "The Password must contains at least one number and special character")
	private String password;
	
	@NotBlank
	@Email
	private String email;

	@NotEmpty
	private List<Role> roleList;

}
