package com.seucafezinho.api_seu_cafezinho.repository;

import com.seucafezinho.api_seu_cafezinho.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Page<Address> findAllByUserId(UUID userId, Pageable pageable);

    boolean existsByUserIdAndStreetIgnoreCase(UUID userId, String street);

    Optional<Address> findByIdAndUserId(UUID addressId, UUID userId);
}