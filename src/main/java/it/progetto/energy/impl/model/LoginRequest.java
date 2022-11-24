package it.progetto.energy.impl.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

	private String userName;
	private String password;
//	private String email;

}
