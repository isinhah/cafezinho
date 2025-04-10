package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Address;
import com.seucafezinho.api_seu_cafezinho.repository.AddressRepository;
import com.seucafezinho.api_seu_cafezinho.repository.UserRepository;
import com.seucafezinho.api_seu_cafezinho.service.impl.AddressServiceImpl;
import com.seucafezinho.api_seu_cafezinho.util.AddressConstants;
import com.seucafezinho.api_seu_cafezinho.util.SecurityContextTestUtil;
import com.seucafezinho.api_seu_cafezinho.util.UserConstants;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.AddressResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.exception.UniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;

    @BeforeEach
    void setup() {
        SecurityContextTestUtil.mockAuthenticatedUser(UserConstants.USER_ID.toString());
        address = buildMockAddress();
    }

    @AfterEach
    void cleanup() {
        SecurityContextTestUtil.clear();
    }

    private Address buildMockAddress() {
        return new Address(
                AddressConstants.ADDRESS_ID,
                AddressConstants.STREET,
                AddressConstants.NUMBER,
                AddressConstants.NEIGHBORHOOD,
                USER
        );
    }

    @Test
    void findById_ShouldReturnAddressResponseDto_WhenSuccessful() {
        when(addressRepository.findById(AddressConstants.ADDRESS_ID)).thenReturn(Optional.of(address));

        AddressResponseDto response = addressService.findById(AddressConstants.ADDRESS_ID);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(AddressConstants.ADDRESS_RESPONSE_DTO);

        verify(addressRepository).findById(AddressConstants.ADDRESS_ID);
    }

    @Test
    void findById_ShouldThrowEntityNotFoundException_WhenAddressDoesNotExist() {
        when(addressRepository.findById(AddressConstants.ADDRESS_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                addressService.findById(AddressConstants.ADDRESS_ID));
    }

    @Test
    void findAllByUser_ShouldReturnPageOfAddressResponseDto_WhenSuccessful() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Address> page = new PageImpl<>(List.of(address));

        when(addressRepository.findAllByUserId(UserConstants.USER_ID, pageable)).thenReturn(page);

        Page<AddressResponseDto> result = addressService.findAllByUser(UserConstants.USER_ID, pageable);

        assertThat(result.getContent())
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(AddressConstants.ADDRESS_RESPONSE_DTO);

        verify(addressRepository).findAllByUserId(UserConstants.USER_ID, pageable);
    }

    @Test
    void create_ShouldSaveAndReturnAddressResponseDto_WhenSuccessful() {
        when(userRepository.findById(UserConstants.USER_ID)).thenReturn(Optional.of(USER));
        when(addressRepository.existsByUserIdAndStreetIgnoreCase(UserConstants.USER_ID, AddressConstants.STREET)).thenReturn(false);
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressResponseDto result = addressService.create(AddressConstants.ADDRESS_REQUEST_DTO, UserConstants.USER_ID);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(AddressConstants.ADDRESS_RESPONSE_DTO);

        verify(addressRepository).save(any(Address.class));
    }

    @Test
    void create_ShouldThrowEntityNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findById(UserConstants.USER_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                addressService.create(AddressConstants.ADDRESS_REQUEST_DTO, UserConstants.USER_ID));
    }

    @Test
    void create_ShouldThrowUniqueViolationException_WhenAddressWithSameStreetAlreadyExists() {
        when(userRepository.findById(UserConstants.USER_ID)).thenReturn(Optional.of(USER));
        when(addressRepository.existsByUserIdAndStreetIgnoreCase(UserConstants.USER_ID, AddressConstants.STREET)).thenReturn(true);

        assertThrows(UniqueViolationException.class, () ->
                addressService.create(AddressConstants.ADDRESS_REQUEST_DTO, UserConstants.USER_ID));
    }

    @Test
    void update_ShouldUpdateAndReturnAddressResponseDto_WhenSuccessful() {
        when(addressRepository.findById(AddressConstants.ADDRESS_ID)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressResponseDto result = addressService.update(AddressConstants.ADDRESS_ID, AddressConstants.ADDRESS_REQUEST_DTO);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(AddressConstants.ADDRESS_RESPONSE_DTO);

        verify(addressRepository).save(any(Address.class));
    }

    @Test
    void update_ShouldThrowEntityNotFoundException_WhenAddressDoesNotExist() {
        when(addressRepository.findById(AddressConstants.ADDRESS_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                addressService.update(AddressConstants.ADDRESS_ID, AddressConstants.ADDRESS_REQUEST_DTO));
    }

    @Test
    void update_ShouldThrowUniqueViolationException_WhenNewStreetAlreadyExistsForUser() {
        Address updatedAddress = buildMockAddress();
        updatedAddress.setStreet("Nova Rua");

        when(addressRepository.findById(AddressConstants.ADDRESS_ID)).thenReturn(Optional.of(updatedAddress));
        when(addressRepository.existsByUserIdAndStreetIgnoreCase(UserConstants.USER_ID, AddressConstants.STREET)).thenReturn(true);

        assertThrows(UniqueViolationException.class, () ->
                addressService.update(AddressConstants.ADDRESS_ID, AddressConstants.ADDRESS_REQUEST_DTO));
    }

    @Test
    void delete_ShouldRemoveAddress_WhenSuccessful() {
        when(addressRepository.findById(AddressConstants.ADDRESS_ID)).thenReturn(Optional.of(address));

        addressService.delete(AddressConstants.ADDRESS_ID);

        verify(addressRepository).delete(address);
    }

    @Test
    void delete_ShouldThrowEntityNotFoundException_WhenAddressDoesNotExist() {
        when(addressRepository.findById(AddressConstants.ADDRESS_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                addressService.delete(AddressConstants.ADDRESS_ID));
    }
}
