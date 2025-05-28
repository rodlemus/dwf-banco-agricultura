package com.udb.bancobas.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Transaction {

    public enum Type {
        deposito, retiro, transferencia
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private BankAccount account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private BigDecimal amount;

    private BigDecimal commission;

    @ManyToOne
    @JoinColumn(name = "related_account_id")
    private BankAccount relatedAccount;

    @ManyToOne
    @JoinColumn(name = "performed_by", nullable = false)
    private User performedBy;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
