package it.progetto.energy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDomain {

    private Long id;

    private String name;

    private String surname;

    private String username;

    private String password;

    private String email;

    private boolean active;

    private List<Role> roleList;

}
