package com.seucafezinho.api_seu_cafezinho.repository;

import com.seucafezinho.api_seu_cafezinho.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
}