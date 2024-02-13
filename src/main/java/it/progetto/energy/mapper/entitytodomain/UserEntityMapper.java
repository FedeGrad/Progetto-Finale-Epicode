package it.progetto.energy.mapper.entitytodomain;

import it.progetto.energy.impl.model.User;
import it.progetto.energy.model.UserDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mapping(target = "active", source = "accountAttivo")
//    @Mapping(target = "roleList", source = "roles") // TODO
    UserDomain fromUserEntityToUserDomain(User user);

    List<UserDomain> fromUserEntityListToUserDomainList(List<User> userList);

//    @Mapping(target = "roles", source = "roleList") // TODO
    @Mapping(target = "accountAttivo", constant = "true")
    User fromUserDomainToUserEntity(UserDomain userDomain);

}
