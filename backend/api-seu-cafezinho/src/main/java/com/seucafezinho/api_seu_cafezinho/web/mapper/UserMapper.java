package com.seucafezinho.api_seu_cafezinho.web.mapper;

import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.web.dto.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone" , target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "role", target = "role", qualifiedByName = "stringToRole")
    User toUser(UserRequestDto createDto);

    @Mapping(target = "role", source = "role", qualifiedByName = "mapRoleToDto")
    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(source = "role", target = "role", qualifiedByName = "stringToRole")
    void updateUserFromDto(UserRequestDto updateDto, @MappingTarget User user);

    @Named("stringToRole")
    default User.Role stringToRole(String role) {
        if (role != null && !role.isEmpty()) {
            return User.Role.valueOf("ROLE_" + role.toUpperCase());
        }
        return User.Role.ROLE_USER;
    }

    @Named("mapRoleToDto")
    default String mapRoleToDto(User.Role role) {
        return role.name().replace("ROLE_", "");
    }

}
