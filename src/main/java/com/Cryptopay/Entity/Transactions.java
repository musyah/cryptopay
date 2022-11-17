package com.Cryptopay.Entity;

import lombok.*;
import javax.persistence.*;

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
    @Column(nullable = false, unique = true, name = "TransactionsId")
    private Integer TransactionsId;
    @Column(nullable = false, unique = true, name = "Name")
    private String Name;
    @Column(nullable = false, unique = true, name = "Amount")
    private Double Amount;
    @Column(nullable = false, name = "pin")
    private String pin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "Wallet_info"
    )
    private Wallet wallet;

    public Transactions(Integer transactionsId, String name, Double amount, String pin, Wallet wallet) {
        TransactionsId = transactionsId;
        Name = name;
        Amount = amount;
        this.pin = pin;
        this.wallet = wallet;
    }
}
