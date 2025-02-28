package com.seucafezinho.api_seu_cafezinho.repository;

import com.seucafezinho.api_seu_cafezinho.entity.Category;
import com.seucafezinho.api_seu_cafezinho.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndCategory(Long productId, Category category);

    Page<Product> findAllByCategory(Category category, Pageable pageable);

    boolean existsByCategoryAndNameIgnoreCase(Category category, String name);
}