package com.seucafezinho.api_seu_cafezinho.web.mapper;

import com.seucafezinho.api_seu_cafezinho.entity.Address;
import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.web.dto.AddressRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.AddressResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "street", target = "street")
    @Mapping(source = "number" , target = "number")
    @Mapping(source = "neighborhood" , target = "neighborhood")
    Address toAddress(AddressRequestDto createDto);

    AddressResponseDto toDto(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateAddressFromDto(AddressRequestDto updateDto, @MappingTarget Address address);

    default Address toEntity(AddressRequestDto dto, User user) {
        Address address = toAddress(dto);
        address.setUser(user);
        return address;
    }
}