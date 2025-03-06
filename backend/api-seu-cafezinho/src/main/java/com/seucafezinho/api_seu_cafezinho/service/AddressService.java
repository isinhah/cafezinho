package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Address;
import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.repository.AddressRepository;
import com.seucafezinho.api_seu_cafezinho.repository.UserRepository;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.AddressRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.AddressResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.exception.UniqueViolationException;
import com.seucafezinho.api_seu_cafezinho.web.mapper.AddressMapper;
import jakarta.persistence.EntityNotFoundException;
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
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public AddressResponseDto findByIdAndUser(UUID userId, UUID addressId) {
        User user = findUserById(userId);
        Address address = findAddressByIdAndUser(addressId, user);
        return AddressMapper.INSTANCE.toDto(address);
    }

    @Transactional(readOnly = true)
    public Page<AddressResponseDto> findAllByUser(UUID userId, Pageable pageable) {
        User user = findUserById(userId);
        return addressRepository.findAllByUser(user, pageable)
                .map(AddressMapper.INSTANCE::toDto);
    }

    @Transactional
    public AddressResponseDto create(AddressRequestDto createDto, UUID userId) {
        User user = findUserById(userId);
        Address address = AddressMapper.INSTANCE.toEntity(createDto, user);
        Address savedAddress = addressRepository.save(address);
        return AddressMapper.INSTANCE.toDto(savedAddress);
    }

    @Transactional
    public AddressResponseDto update(UUID userId, UUID addressId, AddressRequestDto updateDto) {
        User user = findUserById(userId);
        Address existingAddress = findAddressByIdAndUser(addressId, user);

        if (addressRepository.existsByUserAndStreetIgnoreCase(user, updateDto.getStreet())
                && !existingAddress.getStreet().equalsIgnoreCase(updateDto.getStreet())) {
            throw new UniqueViolationException(String.format("Another address with the street name: '%s' already exists for this user", updateDto.getStreet()));
        }

        AddressMapper.INSTANCE.updateAddressFromDto(updateDto, existingAddress);

        Address updatedAddress = addressRepository.save(existingAddress);
        return AddressMapper.INSTANCE.toDto(updatedAddress);
    }

    @Transactional
    public void delete(UUID userId, UUID addressId) {
        User user = findUserById(userId);
        Address address = findAddressByIdAndUser(addressId, user);
        addressRepository.delete(address);
    }

    @Transactional(readOnly = true)
    private Address findAddressByIdAndUser(UUID addressId, User user) {
        return addressRepository.findByIdAndUser(addressId, user)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Address with id: '%s' not found for user: '%s'", addressId, user.getId())
                ));
    }

    @Transactional(readOnly = true)
    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with id: '%s' not found", userId)
                ));
    }
}