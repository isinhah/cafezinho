package com.seucafezinho.api_seu_cafezinho.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
@Table(name = "tb_users")
@SQLDelete(sql = "UPDATE tb_users SET active = false WHERE id=?")
@Where(clause = "is_active = true")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 16)
    private String phone;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(nullable = false, length = 200)
    private String password;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Role role;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    public enum Role {
        ROLE_ADMIN,
        ROLE_USER
    }

    @PrePersist
    private void PrePersist() {
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}