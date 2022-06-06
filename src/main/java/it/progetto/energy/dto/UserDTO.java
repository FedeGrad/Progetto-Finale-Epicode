package it.progetto.energy.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(min = 2, max = 120)
	private String password;
	
	@NotBlank
	@Size(max = 120)
	private String nome;
	
	@NotBlank
	@Size(max = 120)
	private String cognome;
	
	@NotBlank
	@Email
	private String email;
	
	private String roles;

}
