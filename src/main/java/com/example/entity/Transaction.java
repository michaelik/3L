package com.example.entity;

import com.example.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_transaction")
public class Transaction {

    @Id
    UUID id;

    @Column(
            name = "amount",
            precision = 19,
            scale = 2
    )
    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "account_type",
            nullable = false
    )
    AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fk_account_id",
            referencedColumnName = "id"
    )
    private Account account;

    @Column(
            name = "createdAt",
            columnDefinition = "TIMESTAMP NOT NULL"
    )
    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
