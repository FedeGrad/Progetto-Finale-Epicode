package it.progetto.energy.dto.user;

import it.progetto.energy.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOutputDTO {

    private String name;

    private String surname;

    private String username;

    private String email;

    private List<Role> roleList;

}
