package it.progetto.energy.mapper.dtotodomain;

import it.progetto.energy.dto.user.UserDTO;
import it.progetto.energy.dto.user.UserOutputDTO;
import it.progetto.energy.dto.user.UserUpdateDTO;
import it.progetto.energy.model.UserDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    UserDomain fromUserDTOToUserDomain(UserDTO userDTO);

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserDomain fromUserUpdateDTOToUserDomain(UserUpdateDTO userUpdateDTO);

    UserOutputDTO fromUserDomainToUserOutputDTO(UserDomain userDomain);

    List<UserOutputDTO> fromUserDomainListToUserOutputDTOList(List<UserDomain> addressDomainList);

}
