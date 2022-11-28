package com.Cryptopay.Entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "Id")
    private Long Id;
    @Column(nullable = false)
    private LocalDateTime TransactedAt;
    @Column(nullable = false,columnDefinition ="mediumtext")
    private String subject;
    private BigDecimal AmountSent;
    private BigDecimal AmountReceived;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "Wallet_info"
    )
    private Wallet wallet;

    public Transactions(LocalDateTime transactedAt, String subject, BigDecimal amountSent, BigDecimal amountReceived, Wallet wallet) {
        TransactedAt = transactedAt;
        this.subject = subject;
        AmountSent = amountSent;
        AmountReceived = amountReceived;
        this.wallet = wallet;
    }

    public Transactions(LocalDateTime transactedAt, String subject, BigDecimal amountSent, Wallet wallet) {
        TransactedAt = transactedAt;
        this.subject = subject;
        AmountSent = amountSent;
        this.wallet = wallet;
    }

    public Transactions(LocalDateTime transactedAt, String from, BigDecimal amountReceived) {
        TransactedAt = transactedAt;
        this.subject = from;
        AmountReceived = amountReceived;

    }
}
