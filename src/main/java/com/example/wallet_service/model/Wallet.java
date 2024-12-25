package com.example.wallet_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "wallets")
public class Wallet {

    @Id
    @Column(name = "wallet_id", updatable = false, nullable = false)
    @UuidGenerator
    private UUID walletId;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;
}
