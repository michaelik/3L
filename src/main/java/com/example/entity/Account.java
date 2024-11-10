package com.example.entity;

import com.example.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_account",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_customer_account_type",
                        columnNames = {"fk_customer_id", "account_type"}
                )
        })
public class Account {

    @Id
    UUID id;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "account_type",
            nullable = false
    )
    AccountType accountType;

    @Column(
            name = "balance",
            precision = 19,
            scale = 2
    )
    @Builder.Default
    BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fk_customer_id",
            referencedColumnName = "id"
    )
    private Customer customer;

    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Transaction> transactions;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
