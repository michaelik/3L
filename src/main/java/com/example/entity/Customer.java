package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_customer",
        indexes = {@Index(name = "idx_customer_id", columnList = "customer_id", unique = true)})
public class Customer {

    @Id
    UUID id;

    @Column(
            name = "customer_id",
            nullable = false,
            unique = true,
            length = 50
    )
    String customerId;

    @Column(
            nullable = false,
            length = 100
    )
    String surname;

    @Column(
            name = "first_name",
            nullable = false,
            length = 100
    )
    String firstName;

    @Version
    private Long version;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL
    )
    @Builder.Default
    List<Account> accounts = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
