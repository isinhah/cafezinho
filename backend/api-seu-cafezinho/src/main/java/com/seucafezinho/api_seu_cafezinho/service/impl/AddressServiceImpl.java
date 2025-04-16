package com.seucafezinho.api_seu_cafezinho.service.impl;

import com.seucafezinho.api_seu_cafezinho.entity.Address;
import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.repository.AddressRepository;
import com.seucafezinho.api_seu_cafezinho.repository.UserRepository;
import com.seucafezinho.api_seu_cafezinho.service.AddressService;
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

import static com.seucafezinho.api_seu_cafezinho.security.SecurityValidator.validateUserAccess;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public AddressResponseDto findById(UUID addressId) {
        Address address = findAddressById(addressId);
        validateUserAccess(address.getUser().getId());
        return AddressMapper.INSTANCE.toDto(address);
    }

    @Transactional(readOnly = true)
    public Page<AddressResponseDto> findAllByUser(UUID userId, Pageable pageable) {
        validateUserAccess(userId);
        return addressRepository.findAllByUserId(userId, pageable)
                .map(AddressMapper.INSTANCE::toDto);
    }

    @Transactional
    public AddressResponseDto create(AddressRequestDto createDto, UUID userId) {
        validateUserAccess(userId);
        User user = findUserById(userId);

        if (addressRepository.existsByUserIdAndStreetIgnoreCase(userId, createDto.getStreet())) {
            throw new UniqueViolationException(
                    String.format("Another address with the street name: '%s' already exists for this user", createDto.getStreet())
            );
        }

        Address address = AddressMapper.INSTANCE.toEntity(createDto, user);
        Address savedAddress = addressRepository.save(address);
        return AddressMapper.INSTANCE.toDto(savedAddress);
    }

    @Transactional
    public AddressResponseDto update(UUID addressId, AddressRequestDto updateDto) {
        Address existingAddress = findAddressById(addressId);
        validateUserAccess(existingAddress.getUser().getId());

        if (!existingAddress.getStreet().equalsIgnoreCase(updateDto.getStreet()) &&
                addressRepository.existsByUserIdAndStreetIgnoreCase(existingAddress.getUser().getId(), updateDto.getStreet())) {
            throw new UniqueViolationException(
                    String.format("Another address with the street name: '%s' already exists for this user", updateDto.getStreet())
            );
        }

        AddressMapper.INSTANCE.updateAddressFromDto(updateDto, existingAddress);
        Address updatedAddress = addressRepository.save(existingAddress);
        return AddressMapper.INSTANCE.toDto(updatedAddress);
    }

    @Transactional
    public void delete(UUID addressId) {
        Address address = findAddressById(addressId);
        validateUserAccess(address.getUser().getId());
        addressRepository.delete(address);
    }

    @Transactional(readOnly = true)
    public Address findAddressById(UUID addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Address with id: '%s' not found", addressId)
                ));
    }

    @Transactional(readOnly = true)
    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with id: '%s' not found", userId)
                ));
    }
}