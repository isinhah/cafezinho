package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Address;
import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.repository.AddressRepository;
import com.seucafezinho.api_seu_cafezinho.web.dto.AddressRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.AddressResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public AddressResponseDto findByIdAndUser(UUID addressId, User user) {
        Address address = findAddressByIdAndUser(addressId, user);
        return AddressMapper.INSTANCE.toDto(address);
    }

    @Transactional(readOnly = true)
    public Page<AddressResponseDto> findAllByUser(User user, Pageable pageable) {
        return addressRepository.findAllByUser(user, pageable)
                .map(AddressMapper.INSTANCE::toDto);
    }

    // create

    @Transactional
    public AddressResponseDto update(UUID addressId, AddressRequestDto updateDto, User user) {
        Address existingAddress = findAddressByIdAndUser(addressId, user);

        if (addressRepository.existsByUserAndStreetIgnoreCase(user, updateDto.getStreet())
                && !existingAddress.getStreet().equalsIgnoreCase(updateDto.getStreet())) {
            throw new RuntimeException(String.format("Another address with the street name: '%s' already exists for this user", updateDto.getStreet()));
        }

        AddressMapper.INSTANCE.updateAddressFromDto(updateDto, existingAddress);
        Address updatedAddress = addressRepository.save(existingAddress);
        return AddressMapper.INSTANCE.toDto(updatedAddress);
    }

    @Transactional
    public void delete(UUID addressId, User user) {
        Address address = findAddressByIdAndUser(addressId, user);
        addressRepository.delete(address);
    }

    @Transactional(readOnly = true)
    private Address findAddressByIdAndUser(UUID addressId, User user) {
        return addressRepository.findByIdAndUser(addressId, user)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Address with id: '%s' not found for user: '%s'", addressId, user.getId())
                ));
    }
}