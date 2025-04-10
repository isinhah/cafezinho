package com.seucafezinho.api_seu_cafezinho.controller;

import com.seucafezinho.api_seu_cafezinho.service.AddressService;
import com.seucafezinho.api_seu_cafezinho.util.AddressConstants;
import com.seucafezinho.api_seu_cafezinho.web.controller.AddressController;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.AddressRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.AddressResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @Test
    void testGetAddressById_WhenSuccessful() {
        UUID addressId = AddressConstants.ADDRESS_ID;
        AddressResponseDto responseDto = AddressConstants.ADDRESS_RESPONSE_DTO;

        when(addressService.findById(addressId)).thenReturn(responseDto);

        assertDoesNotThrow(() -> {
            addressController.getAddressById(addressId);
        });

        verify(addressService, times(1)).findById(addressId);
    }

    @Test
    void testGetAddressesByUser_WhenSuccessful() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<AddressResponseDto> page = new PageImpl<>(List.of(AddressConstants.ADDRESS_RESPONSE_DTO));

        when(addressService.findAllByUser(USER_ID, pageable)).thenReturn(page);

        assertDoesNotThrow(() -> {
            addressController.getAddressesByUser(USER_ID, pageable);
        });

        verify(addressService, times(1)).findAllByUser(USER_ID, pageable);
    }

    @Test
    void testCreateAddress_WhenSuccessful() {
        AddressRequestDto requestDto = AddressConstants.ADDRESS_REQUEST_DTO;
        AddressResponseDto responseDto = AddressConstants.ADDRESS_RESPONSE_DTO;

        when(addressService.create(requestDto, USER_ID)).thenReturn(responseDto);

        assertDoesNotThrow(() -> {
            addressController.createAddress(USER_ID, requestDto);
        });

        verify(addressService, times(1)).create(requestDto, USER_ID);
    }

    @Test
    void testUpdateAddress_WhenSuccessful() {
        UUID addressId = AddressConstants.ADDRESS_ID;
        AddressRequestDto updateDto = AddressConstants.ADDRESS_REQUEST_DTO;
        AddressResponseDto responseDto = AddressConstants.ADDRESS_RESPONSE_DTO;

        when(addressService.update(addressId, updateDto)).thenReturn(responseDto);

        assertDoesNotThrow(() -> {
            addressController.updateAddress(addressId, updateDto);
        });

        verify(addressService, times(1)).update(addressId, updateDto);
    }

    @Test
    void testDeleteAddress_WhenSuccessful() {
        UUID addressId = AddressConstants.ADDRESS_ID;

        doNothing().when(addressService).delete(addressId);

        assertDoesNotThrow(() -> {
            addressController.deleteAddress(addressId);
        });

        verify(addressService, times(1)).delete(addressId);
    }
}